package com.nilsnahooy.a7minuteworkout.data

import com.nilsnahooy.a7minuteworkout.R
import com.nilsnahooy.a7minuteworkout.utils.AppForContext

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        var exerciseList = ArrayList<ExerciseModel>()

        exerciseList.add(
            ExerciseModel(1, AppForContext.res.getString(R.string.ex_abdominal_crunch),
                45000, R.drawable.ic_abdominal_crunch, isCompleted = false,
                isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(2, AppForContext.res.getString(
                R.string.ex_high_knees_running_in_place), 45000,
                R.drawable.ic_high_knees_running_in_place, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(3, AppForContext.res.getString(R.string.ex_jumping_jacks),
                45000, R.drawable.ic_jumping_jacks, isCompleted = false,
                isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(4, AppForContext.res.getString(R.string.ex_lunge), 30000,
                R.drawable.ic_lunge, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(5, AppForContext.res.getString(R.string.ex_plank), 20000,
                R.drawable.ic_plank, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(6, AppForContext.res.getString(R.string.ex_push_up),
                45000, R.drawable.ic_push_up, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(7, AppForContext.res.getString(R.string.ex_push_up_rotation),
                40000, R.drawable.ic_push_up_and_rotation, isCompleted = false,
                isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(8, AppForContext.res.getString(R.string.ex_side_plank),
                30000, R.drawable.ic_side_plank, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(9, AppForContext.res.getString(R.string.ex_squat), 30000,
                R.drawable.ic_squat, isCompleted = false, isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(10, AppForContext.res.getString(R.string.ex_step_up_onto_chair),
                30000, R.drawable.ic_step_up_onto_chair, isCompleted = false,
                isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(11, AppForContext.res.getString(R.string.ex_triceps_dip_on_chair),
                30000, R.drawable.ic_triceps_dip_on_chair, isCompleted = false,
                isSelected = false)
        )

        exerciseList.add(
            ExerciseModel(12, AppForContext.res.getString(R.string.ex_wall_sit),
                30000, R.drawable.ic_wall_sit, isCompleted = false, isSelected = false)
        )

         exerciseList = exerciseList.shuffled() as ArrayList<ExerciseModel>

        for (e in exerciseList) {
            e.setId(exerciseList.indexOf(e)+1)
        }

        return exerciseList
    }
}