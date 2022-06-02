package com.im_geokjeong.ui.rentalshop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.im_geokjeong.R
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class RentalShopFragment() : Fragment(), MapView.POIItemEventListener, CalloutBalloonAdapter {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = MapView(requireActivity())
        view.findViewById<ConstraintLayout>(R.id.mv_rental_shop).addView(mapView)

        val mapPoint = MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(1, true)

        val marker = MapPOIItem()
        marker.itemName = "수원 화성"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        mapView.setPOIItemEventListener(this)
        mapView.addPOIItem(marker)
    }

    fun openRentalShopDetail(rentalShopId: String) {
        findNavController().navigate(
            R.id.action_rental_shop_to_rental_shop_detail, bundleOf(
                "rental_shop_id" to rentalShopId
            )
        )
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        onSlideUpDialog()
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        onSlideUpDialog()
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
        // 말풍선 클릭 시
        onSlideUpDialog()
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        TODO("Not yet implemented")
    }

    private fun onSlideUpDialog() {
        var contentView: View =
            (requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.popup_slideup,
                null
            )
        val slideupPopup = SlideUpDialog.Builder(requireActivity())
            .setContentView(contentView)
            .create()

        /*slideupPopup.setOnCancelListener{
            Log.d("DIALOG", "close")
        }*/
        contentView.findViewById<TextView>(R.id.btn_popup_detail).setOnClickListener {
            val shopName = contentView.findViewById<TextView>(R.id.tv_popup_rental_name)
            openRentalShopDetail(shopName.toString())
            slideupPopup.dismissAnim()
        }
        slideupPopup.show()
    }

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }

    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }
}

