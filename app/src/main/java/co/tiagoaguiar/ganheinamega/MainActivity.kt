@ml6c5&0
package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    //variável vai ser inicializada depois
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Aqui onde você decide o que o app fazer ...
        setContentView(R.layout.activity_main)

        // Busca os objetos e ter a referências deles
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // db nome do banco de preferências
        // Modo de execução: Context no modo privado apenas o app acessará
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val result = prefs.getString("result", "Nenhum registro salvo")
        //Usar um valor de string  padrão
        // ou retornar null e não mostrar nada.

        //Substituindo if por let
//        result?.let {
//            txtResult.text = "Última aposta: $it"
//        }

        txtResult.text = "Última aposta: $result"

//        if (result != null) {
//            txtResult.text = "Última aposta: $result"
//
//        }


        //opção 1: via XML AndroidManifest.xml
        //android:OnClick="buttonClicket"

        //neste arquivo
        //fun buttonClicked(view: View){
        //Log.i("Teste", "Botão clicado")
        //}

        //opção 2: variável que seja do tipo (objeto anônimo) View.OnClickListener (interface)
        //btnGenerate.setOnClickListener(buttonClickListener)


        //Opção com expressão lambada
//    val buttonClickListener =  View.OnClickListener {
//            Log.i("Teste", "Botão clicado")
//        }
//    //Opção conversional
//    val buttonClickListener = object : View.OnClickListener {
//        //Quem chama o onclick é o próprio SDK do android que dispara após o evento de touch
//        override fun onClick(v: View?) {
//            Log.i("Teste", "Botão clicado")
//        }
        //   }
        //opção 3: Mais simples e recomendado - bloco de código que será disparado pelo OnClickListener

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        //Validar campo vazio
        if (text.isEmpty()) {
            //Vai dar falha
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() //Convert string para int

        if (qtd < 6 || qtd > 15) {
            //deu falha
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random()
        while (true) {
            val number = random.nextInt(60) //0 ..59
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }
        txtResult.text = numbers.joinToString(" - ")

        //Gravar os valores em Prefs
        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply() //sincrona

        // alternativa 2
//        prefs.edit().apply{
//            putString("result", txtResult.text.toString())
//            apply()
//        }

        //val saved = editor.commit() // síncrona
        //Log.i("Teste","foi salvo: $saved")

        //Commit -> salvar de forma sincrona (bloquear a interface)
        //          informa se teve sucesso ou não

        //apply -> salva de forma assincrona (não vai bloquear a interface)
        //        não informa se teve sucesso ou não

    }
}