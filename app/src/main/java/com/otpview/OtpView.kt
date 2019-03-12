/*
 * Copyright 2019 Ankit Chandel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.otpview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.EditText


class OtpView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EditText(context, attrs, defStyleAttr){

    private var space:Float = 24f
    private var charSize:Float = 0f
    private var noOfChars:Float = 0f
    private var lineSpacing:Float = 0f


    init {
        initView(context,attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        setBackgroundResource(0)
        val multi = context.resources.displayMetrics.density
        space = multi * space // convert pixels in your density
        lineSpacing = multi * lineSpacing
        val styleable =
            context.theme.obtainStyledAttributes(attrs, R.styleable.OtpView, 0, 0)
        noOfChars = styleable.getInteger(R.styleable.OtpView_otpLength,2).toFloat()


    }


    override fun onDraw(canvas: Canvas?) {
        val availableWidth = width  - paddingRight - paddingLeft


        if(space < 0){
            charSize = (availableWidth / (noOfChars * 2 - 1))
        }else{
            charSize = (availableWidth - (space * (noOfChars - 1))) / noOfChars
        }

        var start = paddingLeft.toFloat()
        val bottom = (height - paddingBottom).toFloat()


        val text = text
        val textLength = text.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)


        for (i in 0 ..noOfChars.toInt()){
            canvas?.drawLine(start,bottom, start + charSize,bottom,paint)
            if(text.length > i){
                val middle = start + charSize / 2
                canvas?.drawText(text,
                                i,
                                i+1,
                                middle - textWidths[0]/2,
                                bottom - lineSpacing,
                                paint
                    )
            }

            if(space < 0){
                start += charSize * 2
            }else{
                start += charSize + space
            }
        }

    }


}