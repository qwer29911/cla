package com.bangkok.restaurants.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkok.restaurants.data.Restaurant
import com.bangkok.restaurants.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val onItemClick: (Restaurant) -> Unit
) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(restaurant: Restaurant) {
            binding.apply {
                restaurantName.text = restaurant.nazwa
                restaurantRating.text = restaurant.getOcenaDisplay()
                restaurantCuisine.text = restaurant.typKuchni.ifBlank { "-" }
                restaurantDistrict.text = restaurant.dzielnica.ifBlank { "-" }
                restaurantPrice.text = restaurant.getCenyDisplay()
                restaurantDescription.text = restaurant.opis

                // Dystanse pieszo
                val rama9Text = if (restaurant.pieszoRama9.isNotBlank()) {
                    "Rama9: ${restaurant.pieszoRama9} km"
                } else {
                    ""
                }
                distanceRama9.text = rama9Text

                val sukh101Text = if (restaurant.pieszoSukh101.isNotBlank()) {
                    "Sukh101: ${restaurant.pieszoSukh101} km"
                } else {
                    ""
                }
                distanceSukh101.text = sukh101Text
            }
        }
    }

    class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.nazwa == newItem.nazwa && oldItem.adres == newItem.adres
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }
}
