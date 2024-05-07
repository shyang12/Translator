package kr.ac.seokyeong.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kr.ac.seokyeong.translator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var option : FirebaseTranslatorOptions.Builder? = null
    var translater : FirebaseTranslator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        option = FirebaseTranslatorOptions.Builder()

        // 안녕! 내 이름은 James 입니다.

        binding.inputText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                translater?.translate(p0.toString())?.addOnSuccessListener {
                    binding.outTextView.text = it
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 == 0) {
                    // 영어
                    option?.setSourceLanguage(FirebaseTranslateLanguage.EN)
                }else if(p2 == 1) {
                    // 한국어
                    option?.setSourceLanguage(FirebaseTranslateLanguage.KO)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.toSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 == 0) {
                    // 영어
                    option?.setTargetLanguage(FirebaseTranslateLanguage.EN)
                }else if(p2 == 1) {
                    // 한국어
                    option?.setTargetLanguage(FirebaseTranslateLanguage.KO)
                }
                translater = FirebaseNaturalLanguage.getInstance().getTranslator(option!!.build())
                translater?.downloadModelIfNeeded()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
}