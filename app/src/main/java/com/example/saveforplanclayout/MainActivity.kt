package com.example.saveforplanclayout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
        val intent = Intent(this, SavingsExpenses::class.java)
        startActivity(intent)
    }
}