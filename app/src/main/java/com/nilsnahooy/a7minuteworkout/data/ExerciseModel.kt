package com.nilsnahooy.a7minuteworkout.data

class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var duration: Long,
    private var imageRes: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
) {
    fun getId(): Int{
        return id
    }
    fun setId(id:Int){
        this.id = id
    }
    fun getName(): String{
        return name
    }
    fun setName(name:String){
        this.name = name
    }
    fun getDuration():Long {
        return duration
    }
    fun setDuration(duration: Long){
        this.duration = duration
    }
    fun getImageRes():Int{
        return imageRes
    }
    fun setImageRes(imageRes: Int){
        this.imageRes = imageRes
    }
    fun getIsCompleted():Boolean{
        return isCompleted
    }
    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }
    fun getIsSelected(): Boolean{
        return isSelected
    }
    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
}