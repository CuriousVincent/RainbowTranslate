package com.example.vincentwang.rainbowtranslate.util


import android.app.AlertDialog
import android.content.Context
import io.reactivex.Observable
import io.reactivex.ObservableEmitter


/**
 * Created by vincentwang on 2018/3/13.
 */

object DialogUtils {

    fun showDialog(context: Context, title: Int, message: Int): Observable<Boolean>{
        return showDialog(context,context.resources.getString(title),context.resources.getString(title))
    }

    fun showDialog(context: Context, title: String, message: String): Observable<Boolean> {
        return Observable.create(({ e ->
            val dialog = getDialogBuilder(context, title, message, e).create()
            e.setCancellable({
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
            )
            dialog.show()
        }))
    }

    private fun getDialogBuilder(context: Context, title: String, message: String, emitter: ObservableEmitter<Boolean>): AlertDialog.Builder {
        return AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ({ _, _ ->
                    emitter.onNext(true)
                }))
    }
}
