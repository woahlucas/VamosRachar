package com.example.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity() , TextWatcher, TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var edtConta: EditText
    private lateinit var edtNumeroPessoas: EditText
    private lateinit var btnFalar: Button
    private lateinit var btnCompartilhar: FloatingActionButton
    private lateinit var resultado: TextView
    private lateinit var strTTS: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActionBar()?.hide()
        setContentView(R.layout.activity_main)
        edtConta = findViewById<EditText>(R.id.edtConta)
        edtNumeroPessoas = findViewById(R.id.nPessoas)
        btnFalar = findViewById(R.id.btFalar)
        btnCompartilhar = findViewById(R.id.floatingActionButton)
        resultado = findViewById(R.id.resultadoText)
        // Initialize TTS engine
        tts = TextToSpeech(this, this)

        edtConta.addTextChangedListener(this)
        edtNumeroPessoas.addTextChangedListener(this)
        btnCompartilhar.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain2"
            intent.putExtra(Intent.EXTRA_TEXT, strTTS)
            startActivity(intent)
        }
        btnFalar.setOnClickListener {
                tts.speak(strTTS, TextToSpeech.QUEUE_FLUSH, null, "ID1")
        }
    }

//    private fun calcularDivisao() {
//        val valorConta = edtConta.text.toString().toDoubleOrNull() ?: 0.0
//        val numeroPessoas = edtNumeroPessoas.text.toString().toIntOrNull() ?: 1
//
//        val valorPorPessoa = valorConta / numeroPessoas
//        val textoResultado = String.format(Locale.getDefault(), "Valor por pessoa: R$%.2f", valorPorPessoa)
//        tts.speak(textoResultado, TextToSpeech.QUEUE_FLUSH, null, null)
//    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
       Log.d("PDM23","Antes de mudar")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("PDM23","Mudando")
    }

    override fun afterTextChanged(s: Editable?) {
        var valorConta = edtConta.text.toString()
        var nPessoas = edtNumeroPessoas.text.toString()

        if (valorConta != "" && nPessoas != "") {
            val valorContaDouble = valorConta.toDoubleOrNull() ?: 0.0
            val nPessoasInt = nPessoas.toIntOrNull() ?: 1

            val valorPorPessoa = valorContaDouble / nPessoasInt
            val resultadoTexto = String.format(Locale.getDefault(), "Valor por pessoa: R$%.2f", valorPorPessoa)

            resultado.text = resultadoTexto

            strTTS = "A conta de R$$valorConta entre $nPessoas pessoas fica $valorPorPessoa para cada!"
        } else {
            resultado.text = "R$: 0,00"
        }
    }

    override fun onDestroy() {
            // Release TTS engine resources
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }

    override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // TTS engine is initialized successfully
                tts.language = Locale.getDefault()
                Log.d("PDM23","Sucesso na Inicialização")
            } else {
                // TTS engine failed to initialize
                Log.e("PDM23", "Failed to initialize TTS engine.")
            }
        }


}

