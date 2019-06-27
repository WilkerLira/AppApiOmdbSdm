package br.edu.ifsp.scl.omdbapisdmkt.utils

import br.edu.ifsp.scl.appomdbapisdm.data.OMDb
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.Constantes.APP_KEY_FIELD
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.Constantes.APP_KEY_VALUE
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.Constantes.URL_BASE
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.codigosMensagen.RESPOSTA_BUSCA
import br.edu.ifsp.scl.omdbapisdmkt.fragment.FragmentDetalheFilme
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_detalhe_filme.*
import org.jetbrains.anko.design.snackbar
import org.json.JSONException
import org.json.JSONObject

class OmdbItem(val modoItemFragment: FragmentDetalheFilme) {
    fun buscar(imdbID: String?) {
        val urlSb = StringBuilder(URL_BASE)
        with(urlSb) {append("i=${imdbID}&${APP_KEY_FIELD}=${APP_KEY_VALUE}")}
        val url = urlSb.toString()
        val filaRequisicao: RequestQueue = Volley.newRequestQueue(modoItemFragment.context)
        var buscaJORequest: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,RespostaListener(),ErroListener()){}
        filaRequisicao.add(buscaJORequest)
    }

    inner class RespostaListener : Response.Listener<JSONObject> {
        override fun onResponse(response: JSONObject?) {
            try {
                val gson = Gson()
                val omdb: OMDb = gson.fromJson(response.toString(), OMDb::class.java)
                modoItemFragment.itemHandler.obtainMessage(RESPOSTA_BUSCA, omdb).sendToTarget()
            } catch (jse: JSONException) {
                modoItemFragment.layout_item.snackbar("Erro na conversão JSON")
            }
        }
    }
    inner class ErroListener : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            modoItemFragment.layout_item.snackbar("Erro na requisição: ${error.toString()}")
        }
    }
}