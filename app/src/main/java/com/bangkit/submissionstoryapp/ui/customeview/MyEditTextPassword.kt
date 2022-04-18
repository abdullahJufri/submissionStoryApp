package com.bangkit.submissionstoryapp.ui.customeview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

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

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isUnvalidPassword(s)

            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }


    private fun isUnvalidPassword(password: CharSequence): Boolean {
        passwordLength = password.length
        if (passwordLength < 6){
            error = "Kurang dari 6"
        }
       return true

    }
}