package com.im_geokjeong.ui.office

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.im_geokjeong.R
import com.im_geokjeong.R.id
import com.im_geokjeong.common.KEY_OFFICE_ID
import com.im_geokjeong.common.KEY_OFFICE_NAME
import com.im_geokjeong.common.MyLocationManager
import com.im_geokjeong.databinding.FragmentOfficeBinding
import com.im_geokjeong.databinding.PopupSlideupBinding
import com.im_geokjeong.model.Office
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import com.im_geokjeong.ui.searchcrop.SearchCropDialog
import kotlinx.android.synthetic.main.fragment_office.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class OfficeFragment() : Fragment(), MapView.POIItemEventListener, CalloutBalloonAdapter {

    private val ACCESS_FINE_LOCATION = 1000
    private val viewModel: OfficeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var officeList: List<Office>
    lateinit var slideupPopup: SlideUpDialog
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
            Toast.makeText(requireContext(), "GPS??? ????????????", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

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

        viewModel.openOfficeEvent.observe(viewLifecycleOwner, EventObserver {
            openOfficeDetail(it.id, it.name)
        })

        NavHostFragment.findNavController(this)
            .currentBackStackEntry?.savedStateHandle?.getLiveData<String>("machineName")
            ?.observe(viewLifecycleOwner, Observer {
                viewModel.loadOfficeList(it, findLocation().first, findLocation().second)
                setAdapter(it)
            })

        val btnFindLocation = view.findViewById<ImageButton>(R.id.btnFindLocation)
        btnFindLocation.setOnClickListener {
            findLocation()
        }

        btnFarmer.setOnClickListener {
            val dialog = SearchCropDialog()
            dialog.show(requireActivity().supportFragmentManager, "SearchCropDialog")
        }

    }

    private fun setAdapter(machineName: String) {
        viewModel.loadOfficeList(machineName, findLocation().first, findLocation().second)

        val officeAdapter = OfficeAdapter(OfficeAdapter.OfficeListener { office ->
            val officeLatitude = office.latitude?.toDouble()
            val officeLongitude = office.longitude?.toDouble()
            val officePosition = MapPoint.mapPointWithGeoCoord(officeLatitude!!, officeLongitude!!)

            binding.mvRentalOffice.setMapCenterPoint(officePosition, true)
        })
        binding.rvOfficeList.adapter = officeAdapter

        viewModel.officeList.observe(viewLifecycleOwner) {
            officeAdapter.submitList(it)
        }
    }

    private fun openOfficeDetail(rentalOfficeId: Long, rentalOfficeName: String) {
        slideupPopup.dismissAnim()

        findNavController().navigate(
            R.id.action_office_to_office_detail, bundleOf(
                KEY_OFFICE_ID to rentalOfficeId,
                KEY_OFFICE_NAME to rentalOfficeName
            )
        )
    }

    private fun permissionCheck() {
        val preference = requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // ????????? ?????? ??????
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // ?????? ??????
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("?????? ????????? ?????????????????? ?????? ????????? ??????????????????.")
                builder.setPositiveButton("??????") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("??????") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // ?????? ?????? ??????
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION
                    )
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("?????? ????????? ?????????????????? ???????????? ?????? ????????? ??????????????????.")
                    builder.setPositiveButton("???????????? ??????") { dialog, which ->
                        val packageName = "com.im_geokjeong"
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("??????") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {

        }
    }

    // ?????? ??????
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "?????? ????????? ?????????????????????", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "?????? ????????? ?????????????????????", Toast.LENGTH_SHORT).show()

            }
        }
    }

    lateinit var lm: LocationManager
    private fun checkLocationService(): Boolean {
        lm = MyLocationManager.getLocationManager(requireContext())
        //val locationManager =  requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // ?????? ????????? ??????
    @SuppressLint("MissingPermission")
    private fun findLocation(): Pair<String, String> {

        val userNowLocation = MyLocationManager.getLocation(lm)

        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        // ??? ????????? ?????? ??????
        val marker = MapPOIItem()
        marker.apply {
            itemName = "??? ??????"
            mapPoint = uNowPosition
            markerType = MapPOIItem.MarkerType.RedPin
        }

        binding.mvRentalOffice.setMapCenterPoint(uNowPosition, true)
        binding.mvRentalOffice.addPOIItem(marker)

        return Pair(uLatitude.toString(), uLongitude.toString())
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (p1!!.itemName.equals("??? ??????")) {

        } else {
            onSlideUpDialog(p1!!)
            binding.rvOfficeList.visibility = View.GONE;
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        if (p1!!.itemName.equals("??? ??????")) {

        } else {
            onSlideUpDialog(p1!!)
            binding.rvOfficeList.visibility = View.GONE;

        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        // ????????? ?????? ???
        if (poiItem!!.itemName.equals("??? ??????")) {

        } else {
            onSlideUpDialog(poiItem!!)
            binding.rvOfficeList.visibility = View.GONE;

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
        val name: TextView = mCalloutBalloon.findViewById(id.ballTvName)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // ?????? ?????? ??? ????????? ?????????
            name.text = poiItem?.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // ????????? ?????? ???
            return mCalloutBalloon
        }
    }

}
