package br.edu.ifsp.scl.omdbapisdmkt.fragment

import android.support.v4.app.Fragment
import android.view.View
import java.util.*

abstract class ModoApp: Fragment() {
    object codigosMensagen { val RESPOSTA_BUSCA = 0}

    object Constantes {
        val URL_BASE = "http://www.omdbapi.com/?"
        val APP_KEY_FIELD = "apikey"
        val APP_KEY_VALUE = "80f714fd" // Preeencher com seu app_key
    }
}