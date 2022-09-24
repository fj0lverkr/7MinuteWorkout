package com.nilsnahooy.a7minuteworkout

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nilsnahooy.a7minuteworkout.databinding.ActivityHistoryBinding
import com.nilsnahooy.a7minuteworkout.workoutHistory.HistoryAdapter
import com.nilsnahooy.a7minuteworkout.workoutHistory.WorkoutDao
import com.nilsnahooy.a7minuteworkout.workoutHistory.WorkoutEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    var b : ActivityHistoryBinding? = null
    private lateinit var dao: WorkoutDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        b = ActivityHistoryBinding.inflate(layoutInflater)
        dao = (application as WorkoutApp).db.workoutDao()
        setContentView(b?.root)

        setSupportActionBar(b?.tbHistory)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        b?.tbHistory?.setNavigationOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            dao.selectAllWorkouts().collect{
                setupData(ArrayList(it))
            }
        }
    }

    private fun setupData(items: ArrayList<WorkoutEntity>){
        if (items.isNotEmpty()){
            val adapter = HistoryAdapter(items, ::showDeleteItemDialog)
            b?.rvExerciseItems?.layoutManager = LinearLayoutManager(this)
            b?.rvExerciseItems?.adapter = adapter
            b?.rvExerciseItems?.visibility = View.VISIBLE
            b?.tvNoItems?.visibility = View.GONE
        }else{
            b?.rvExerciseItems?.visibility = View.GONE
            b?.tvNoItems?.visibility = View.VISIBLE
        }
    }

    private fun showDeleteItemDialog(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_delete_record))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.lbl_yes)){ dialogInterface, _ ->
            lifecycleScope.launch {
                dao.delete(WorkoutEntity(id))
                Toast.makeText(applicationContext, getString(R.string.toast_delete_confirmed),
                    Toast.LENGTH_LONG).show()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(getString(R.string.lbl_no)){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}