package com.zapir.ariadne.ui.search.list

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.zapir.ariadne.model.entity.Point
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.zapir.ariadne.R
import kotlinx.android.synthetic.main.item_point.view.*

class PointsAdapter(private val clickListener: (Point) -> Unit) :
        RecyclerView.Adapter<PointsAdapter.ViewHolder>(),
        Filterable {

    var itemPoints: MutableList<PointListItem> = mutableListOf()
    var finallyItemPoints: MutableList<PointListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = PointsAdapter
            .ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent,
                    false), clickListener)

    override fun getItemCount() = itemPoints.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alert = (itemPoints[position]).point
        holder.bind(alert)
    }

    fun setData(points: List<Point>) {
        itemPoints.clear()
        itemPoints.addAll(points.map { PointListItem(it) })
        finallyItemPoints.clear()
        finallyItemPoints.addAll(points.map { PointListItem(it) })

        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.e("NotificationActivity", constraint.toString())
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    itemPoints.clear()
                    itemPoints.addAll(finallyItemPoints)
                } else {
                    itemPoints.clear()
                    itemPoints = finallyItemPoints.filter {
                        val point = (it).point
                        point.name
                                ?.toLowerCase()
                                ?.contains(charString.toLowerCase()) ?: false
                    }.toMutableList()
                }
                val results = FilterResults()
                results.values = itemPoints
                return results
            }//ToDo: make adapter smarter

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.e("NotificationActivity", " result ${constraint.toString()}")
                itemPoints = results?.values as MutableList<PointListItem>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(val view: View, clickListener: (Point) -> Unit) : RecyclerView.ViewHolder(view) {
        private lateinit var point: Point

        init {
            view.setOnClickListener { clickListener.invoke(point) }
        }

        fun bind(point: Point) {
            this.point = point
            view.item_point_tv.text = point.name

        }
    }
}
