package com.bagus.pemantauanbencana.ui.infowindow

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.ui.disasterdetail.DisasterDetailActivity
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MarkerWindow(mapView: MapView, private var item: DisasterEntity):
    InfoWindow(R.layout.info_window, mapView) {

    override fun onOpen(item: Any?) {
        closeAllInfoWindowsOn(mapView)
        
        val tvDisaster = mView.findViewById<TextView>(R.id.tv_disaster)
        val tvEventDate = mView.findViewById<TextView>(R.id.tv_eventdate)
        val tvProvince = mView.findViewById<TextView>(R.id.tv_province)
        val detailButton = mView.findViewById<Button>(R.id.btn_detail)

        val splitEventDate = this.item.eventdate!!.split(" ").toTypedArray()

        tvDisaster.text = this.item.disastertype
        tvEventDate.text = splitEventDate[0]
        tvProvince.text = this.item.regencyCity

        detailButton.setOnClickListener {
            val intent = Intent(detailButton.context, DisasterDetailActivity::class.java)
            intent.putExtra(DisasterDetailActivity.EXTRA_DISASTER, this.item.idLogs)
            detailButton.context.startActivity(intent)
        }

        mView.setOnClickListener {
            close()
        }
    }

    override fun onClose() {
        close()
    }

}