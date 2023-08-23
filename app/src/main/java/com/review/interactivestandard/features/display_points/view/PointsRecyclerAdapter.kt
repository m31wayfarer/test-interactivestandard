package com.review.interactivestandard.features.display_points.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.review.interactivestandard.databinding.ItemViewpointBinding
import com.review.interactivestandard.features.display_points.view.dto.TableViewPointDTO

class PointsRecyclerAdapter :
    ListAdapter<TableViewPointDTO, PointsRecyclerAdapter.ViewHolder>(TableViewPointDiffCallback()) {

    class ViewHolder(private val binding: ItemViewpointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TableViewPointDTO) {
            binding.textViewX.text = item.x
            binding.textViewY.text = item.y
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewpointBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TableViewPointDiffCallback : DiffUtil.ItemCallback<TableViewPointDTO>() {
    override fun areItemsTheSame(oldItem: TableViewPointDTO, newItem: TableViewPointDTO): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(
        oldItem: TableViewPointDTO,
        newItem: TableViewPointDTO
    ): Boolean {
        return oldItem == newItem
    }
}