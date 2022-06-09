package com.im_geokjeong.ui.rentaloffice

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.im_geokjeong.databinding.PopupSlideupBinding
import com.im_geokjeong.model.Office
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.lang.NullPointerException

class RentalOfficeFragment() : Fragment(), MapView.POIItemEventListener, CalloutBalloonAdapter {

    private val viewModel: RentalOfficeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var officeList: List<Office>
    lateinit var slideupPopup : SlideUpDialog

    var officeMap = HashMap<String, Office>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_office, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapView = MapView(requireActivity())

        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
        view.findViewById<ConstraintLayout>(R.id.mv_rental_office).addView(mapView)

        viewModel.items.observe(viewLifecycleOwner) {
            Log.d("RentalOfficeFragment", "items=${it.data}")
            officeList = it.data

            for (office in officeList) {
                officeMap.put(office.name, office)

                val marker = MapPOIItem()

                val position = MapPoint.mapPointWithGeoCoord(
                    office.latitude.toDouble(),
                    office.longitude.toDouble()
                )

                //marker.id = office.id
                marker.itemName = office.name
                marker.mapPoint = position
                marker.markerType = MapPOIItem.MarkerType.BluePin
                marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

                mapView.setPOIItemEventListener(this@RentalOfficeFragment)
                mapView.addPOIItem(marker)
            }
        }

        viewModel.openOfficeEvent.observe(viewLifecycleOwner, EventObserver{
            openOfficeDetail(it.id, it.name)

        })

        val btnFindLocation = view.findViewById<ImageButton>(R.id.btn_find_location)
        btnFindLocation.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val locationManager: LocationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

                try {
                    val userNowLocation: Location? =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    val uLatitude = userNowLocation?.latitude
                    val uLongitude = userNowLocation?.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)
                    Log.e("LOCATION_NOW", "latitude=${uLatitude} + longitude=${uLongitude}")

                    mapView.setMapCenterPointAndZoomLevel(uNowPosition, 1, true)

                    val marker = MapPOIItem()
                    marker.itemName = "내위치"
                    marker.mapPoint = uNowPosition
                    marker.markerType = MapPOIItem.MarkerType.BluePin
                    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

                    mapView.setPOIItemEventListener(this)
                    mapView.addPOIItem(marker)

                } catch (e: NullPointerException) {
                    Log.e("LOCATION_ERROR", e.toString())
                    ActivityCompat.finishAffinity(requireActivity())
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
                )
            }
        }
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
        onSlideUpDialog(p1!!)
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        onSlideUpDialog(p1!!)
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        // 말풍선 클릭 시
        onSlideUpDialog(poiItem!!)
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
