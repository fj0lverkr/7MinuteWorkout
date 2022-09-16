package com.nilsnahooy.a7minuteworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        exerciseList.add(
            ExerciseModel(1,"abdominal crunch", 30000,
                R.drawable.ic_abdominal_crunch, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(2,"high knees running in place", 30000,
                R.drawable.ic_high_knees_running_in_place, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(3,"jumping jacks", 30000,
                R.drawable.ic_jumping_jacks, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(4,"lunge", 30000,
                R.drawable.ic_lunge, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(5,"plank", 30000,
                R.drawable.ic_plank, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(6,"push-up", 30000,
                R.drawable.ic_push_up, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(7,"push-up and rotation", 30000,
                R.drawable.ic_push_up_and_rotation, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(8,"side plank", 30000,
                R.drawable.ic_side_plank, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(9,"squat", 30000,
                R.drawable.ic_squat, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(10,"step up onto chair", 30000,
                R.drawable.ic_step_up_onto_chair, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(11,"triceps dip on chair", 30000,
                R.drawable.ic_triceps_dip_on_chair, isCompleted = false, isSelected = false))

        exerciseList.add(
            ExerciseModel(12,"wall sit", 30000,
                R.drawable.ic_wall_sit, isCompleted = false, isSelected = false))

        return exerciseList.shuffled() as ArrayList<ExerciseModel>
    }
}