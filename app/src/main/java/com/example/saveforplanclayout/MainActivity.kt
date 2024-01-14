package com.example.saveforplanclayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
    }

    fun onClickAddPlans(view: View) {
        val intent = Intent(this, AddPlan::class.java)
        startActivity(intent)
    }

    fun onClickViewPlan(view: View) {
        val intent = Intent(this, ViewPlan::class.java)
        startActivity(intent)
    }

    fun onClickBankAccountExpenses(view: View) {
        val intent = Intent(this, SalaryExpenses::class.java)
        startActivity(intent)
    }
}