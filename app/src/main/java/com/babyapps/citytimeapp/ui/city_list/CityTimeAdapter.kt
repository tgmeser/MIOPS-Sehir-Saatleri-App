package com.babyapps.citytimeapp.ui.city_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.citytimeapp.data.City
import com.babyapps.citytimeapp.databinding.ItemCityBinding

class CityTimeAdapter : RecyclerView.Adapter<CityTimeAdapter.CityTimeViewHolder>() {

    inner class CityTimeViewHolder(val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    val diffCallback = object : DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(
            oldItem: City,
            newItem: City
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: City,
            newItem: City
        ): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityTimeViewHolder =
        CityTimeViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CityTimeViewHolder, position: Int) {
        val city = differ.currentList[position]

        holder.binding.apply {
            //tvCity.text = city.name
            tvCity.text = city.name
            analogClock.setBackgroundColor(Color.parseColor(city.colorCode))
            tvDifference.text = city.timeDifference

        }

        holder.binding.root.setOnClickListener { onItemClickListener?.let { it(city) } }

    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((City) -> Unit)? = null

    fun setOnItemClickListener(listener: (City) -> Unit) {
        onItemClickListener = listener
    }

}