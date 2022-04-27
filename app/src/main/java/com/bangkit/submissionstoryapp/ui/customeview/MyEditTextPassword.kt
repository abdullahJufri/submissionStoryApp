package com.bangkit.submissionstoryapp.ui.customeview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.bangkit.submissionstoryapp.R

class MyEditTextPassword: AppCompatEditText {
    private var passwordLength = 0
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        super.onDraw(canvas)
    }

    private fun init(){

        doOnTextChanged { s, start, before, count ->
            if (s != null) {
                isUnvalidPassword(s)
            }
        }
    }


    private fun isUnvalidPassword(password: CharSequence): Boolean {
        passwordLength = password.length
        if (passwordLength < 6){
            error = context.getString(R.string.invalid_password)
        }
       return true

    }
}