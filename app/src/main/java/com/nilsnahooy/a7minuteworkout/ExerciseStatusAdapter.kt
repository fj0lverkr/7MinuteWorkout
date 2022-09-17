package com.nilsnahooy.a7minuteworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.nilsnahooy.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val exerciseList: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

        inner class ViewHolder(b: ItemExerciseStatusBinding):
                RecyclerView.ViewHolder(b.root){
                    val tvItem = b.tvItem
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val c = holder.itemView.context
        val model: ExerciseModel = exerciseList[position]
        val textColor: Int = if (model.getIsSelected()){
           R.color.primary
        } else {
            R.color.accent
        }

        holder.tvItem.text = model.getId().toString()
        holder.tvItem.background =
            if(model.getIsSelected()){
                AppCompatResources.getDrawable(c,
                    R.drawable.item_circular_progress_accent_background)
            }else if (model.getIsCompleted()){
                AppCompatResources.getDrawable(c, R.drawable.item_circular_primary_background)
            } else {
                AppCompatResources.getDrawable(c, R.drawable.item_circular_grey_background)
            }
        holder.tvItem.setTextColor(c.resources.getColor(textColor, c.theme))
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}