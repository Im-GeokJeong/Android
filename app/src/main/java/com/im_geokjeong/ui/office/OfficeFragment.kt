package com.im_geokjeong.ui.office

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.im_geokjeong.R
import com.im_geokjeong.R.id
import com.im_geokjeong.common.KEY_OFFICE_ID
import com.im_geokjeong.common.KEY_OFFICE_NAME
import com.im_geokjeong.databinding.FragmentOfficeBinding
import com.im_geokjeong.databinding.PopupSlideupBinding
import com.im_geokjeong.model.Office
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class OfficeFragment() : Fragment(), MapView.POIItemEventListener, CalloutBalloonAdapter {

    private val ACCESS_FINE_LOCATION = 1000
    private val viewModel: OfficeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var officeList: List<Office>
    lateinit var slideupPopup : SlideUpDialog
    private lateinit var binding: FragmentOfficeBinding

    var officeMap = HashMap<String, Office>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfficeBinding.inflate(inflater, container, false)

        if (checkLocationService()) {
            permissionCheck()
        } else {
            Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapView = binding.mvRentalOffice



        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))

        viewModel.offices.observe(viewLifecycleOwner) {
            officeList = it.data

            for (office in officeList) {
                officeMap.put(office.name, office)

                val marker = MapPOIItem()

                val position = MapPoint.mapPointWithGeoCoord(
                    office.latitude!!.toDouble(),
                    office.longitude!!.toDouble()
                )

                //marker.id = office.id
                marker.itemName = office.name
                marker.mapPoint = position
                marker.markerType = MapPOIItem.MarkerType.BluePin
                marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

                mapView.setPOIItemEventListener(this@OfficeFragment)
                mapView.addPOIItem(marker)
            }
        }

        viewModel.openOfficeEvent.observe(viewLifecycleOwner, EventObserver{
            openOfficeDetail(it.id, it.name)
        })

        val btnFindLocation = view.findViewById<ImageButton>(R.id.btn_find_location)
        btnFindLocation.setOnClickListener {
            findLocation()
        }
    }

    private fun permissionCheck() {
        val preference =  requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val packageName = "com.im_geokjeong"
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {

        }
    }

    // 권한 요청
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager =  requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 현재 사용자 위치
    @SuppressLint("MissingPermission")
    private fun findLocation() {
        val lm: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        // 현 위치에 마커 찍기
        val marker = MapPOIItem()
        marker.apply {
            itemName = "현 위치"
            mapPoint =uNowPosition
            markerType = MapPOIItem.MarkerType.RedPin
        }

        binding.mvRentalOffice.setMapCenterPoint(uNowPosition, true)
        binding.mvRentalOffice.addPOIItem(marker)
    }

    private fun openOfficeDetail(rentalOfficeId: Int, rentalOfficeName: String) {
        slideupPopup.dismissAnim()

        findNavController().navigate(
            R.id.action_office_to_office_detail, bundleOf(
                KEY_OFFICE_ID to rentalOfficeId,
                KEY_OFFICE_NAME to rentalOfficeName
            )
        )
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (p1!!.itemName.equals("현 위치")) {

        } else {
            onSlideUpDialog(p1!!)
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        if (p1!!.itemName.equals("현 위치")) {

        } else {
            onSlideUpDialog(p1!!)
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        // 말풍선 클릭 시
        if (poiItem!!.itemName.equals("현 위치")) {

        } else {
            onSlideUpDialog(poiItem!!)
        }
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        TODO("Not yet implemented")
    }

    private fun onSlideUpDialog(p: MapPOIItem) {

        val binding = PopupSlideupBinding.inflate(LayoutInflater.from(requireContext()))

        val office: Office? = officeMap[p.itemName]
        binding.office = office
        binding.viewModel = viewModel

        slideupPopup = SlideUpDialog.Builder(requireActivity())
            .setContentView(binding.root)
            .create()

        /*slideupPopup.setOnCancelListener{
            Log.d("DIALOG", "close")
        }*/

        slideupPopup.setCanceledOnTouchOutside(true)

        slideupPopup.show()
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }

    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }

    class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        private val mCalloutBalloon: View = inflater.inflate(R.layout.item_balloon, null)
        val name: TextView = mCalloutBalloon.findViewById(id.ball_tv_name)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            return mCalloutBalloon
        }
    }
}
