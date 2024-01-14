package com.example.saveforplanclayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ViewPlan :AppCompatActivity(){

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_plan)

        sharedPreferenceManager = SharedPreferenceManager(this)

        val backButton: Button = findViewById(R.id.btnBack)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Clear Plans Button
        val clearPlansButton: Button = findViewById(R.id.btn_clearplan)
        clearPlansButton.setOnClickListener {
            // Call the clearAll function from SharedPreferenceManager
            sharedPreferenceManager.clearAll(this)

            // Clear all plans
            val plansContainer: LinearLayout = findViewById(R.id.plansContainer)
            plansContainer.removeAllViews()

            // Display a message indicating that plans are cleared
            Toast.makeText(this, "Plans cleared successfully!", Toast.LENGTH_SHORT).show()
        }

        displayPlansAndMonths()
    }

    private fun displayPlansAndMonths() {
        // LinearLayout to show plans
        val plansContainer: LinearLayout = findViewById(R.id.plansContainer)

        // Retrieve the list of plans from SharedPreferences
        val loadedPlansList = sharedPreferenceManager.getPlansList()

        // For loop to show the list of plans
        for (plan in loadedPlansList) {
            val planView = createPlanView(plan)
            plansContainer.addView(planView)
        }
    }

    private fun createPlanView(plan: AddPlan.Plan): View {
        val planTextView = TextView(this)
        val monthsNeeded = sharedPreferenceManager.getMonthsNeeded()

        // Set text with plan name and months needed
        planTextView.text = "Plan: ${plan.name}\n Amount: ${plan.totalAmount}, Months Needed: $monthsNeeded"
        planTextView.textSize = 18f
        planTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        return planTextView
    }
}