package admin.com.example.securitygenerator;

import android.app.Activity;
import android.app.AlertDialog;

public class MensagemAlerta {

	public static void alerta(String TituloAlerta, String MensagemAlerta,Activity activity){
		AlertDialog.Builder Mensagem = new AlertDialog.Builder(activity);
		Mensagem.setTitle(TituloAlerta);
		Mensagem.setMessage(MensagemAlerta);
		Mensagem.setNeutralButton("Ok", null);
		Mensagem.show();
	}

}
