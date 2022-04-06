package com.bagus.pemantauanbencana.ui.disasterlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ItemsDisasterBinding
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.ui.disasterdetail.DisasterDetailActivity
import com.bumptech.glide.Glide

class DisasterAdapter : RecyclerView.Adapter<DisasterAdapter.DisasterViewHolder>() {
    private var listDisaster = ArrayList<DisasterEntity>()

    fun setDisaster(disaster: List<DisasterEntity>?) {
        if(disaster == null) return
        this.listDisaster.clear()
        this.listDisaster.addAll(disaster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterViewHolder {
        val itemsDisasterBinding = ItemsDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisasterViewHolder(itemsDisasterBinding)
    }

    override fun onBindViewHolder(holder: DisasterViewHolder, position: Int) {
        val disaster = listDisaster[position]
        holder.bind(disaster)
    }

    override fun getItemCount(): Int = listDisaster.size

    class DisasterViewHolder(private val binding: ItemsDisasterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(disaster: DisasterEntity) {
            with(binding) {
                when (disaster.typeid) {
                    "DROUGHT" -> imgDisaster.setImageResource(R.drawable.drought_high)
                    "EARTHQUAKE" ->  imgDisaster.setImageResource(R.drawable.earthquake_high)
                    "FLOOD" -> imgDisaster.setImageResource(R.drawable.flood_high)
                    "HIGHSURF" -> imgDisaster.setImageResource(R.drawable.highsurf_high)
                    "HIGHWIND" -> imgDisaster.setImageResource(R.drawable.highwind_high)
                    "INCIDENT" -> imgDisaster.setImageResource(R.drawable.incident_high)
                    "LANDSLIDE" -> imgDisaster.setImageResource(R.drawable.landslide_high)
                    "TORNADO" -> imgDisaster.setImageResource(R.drawable.tornado_high)
                    "VOLCANO" -> imgDisaster.setImageResource(R.drawable.volcano_high)
                    "WILDFIRE" -> imgDisaster.setImageResource(R.drawable.wildfire_high)
                }
                tvItemDisaster.text = disaster.disastertype
                tvCity.text = disaster.regencyCity
                tvEventdate.text = disaster.eventdate

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DisasterDetailActivity::class.java)
                    intent.putExtra(DisasterDetailActivity.EXTRA_DISASTER, disaster.idLogs)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}