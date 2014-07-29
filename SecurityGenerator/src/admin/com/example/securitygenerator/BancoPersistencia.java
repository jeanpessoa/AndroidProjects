package admin.com.example.securitygenerator;

import admin.com.example.securitygenerator.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity.ListItem;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoPersistencia extends Activity{
	EditText EditNome, EditSenha;
	Button Salvar;
	Button Buscar;
	Button Apagar;
	//usado pelo criabanco
	static String nomeBanco = "Cadastro";
	static SQLiteDatabase BancoDados = null;
	Cursor cursor;
	ListView MostraDados;
	SimpleCursorAdapter AdaptaLista;
	public static final String KEY_NOME_PESSOA = "nomePessoa";
	static int id = -1;
	static String Aid;

	private void Inicializar(){
		
			EditNome = (EditText) findViewById(R.id.idnome);
			EditSenha = (EditText) findViewById(R.id.idsenha);
			
			Salvar = (Button) findViewById(R.id.BtnSalvar);
			Apagar = (Button) findViewById(R.id.BtnApagar);
			Apagar.setVisibility(View.INVISIBLE);
			//Buscar = (Button) findViewById(R.id.BtnMostrar);
			AbrirBanco();
			//MostraDados = (ListView) findViewById(R.id.MostraDados1);
			//GravaBanco();
//			CarregaDado();
//			btnSalvarDados();
//			btnApagarDados();
			FecharBanco();
			
			
		
		
	}
	
	private void AbrirBanco(){
		//
		try{
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			String SQL = "CREATE TABLE IF NOT EXISTS tabCadastroPessoa(_id INTEGER PRIMARY KEY, nomePessoa TEXT, senhaPessoa TEXT)";
			BancoDados.execSQL(SQL);
			//MensagemAlerta("Banco de Dados", "Banco criado com sucesso!");
		}catch(Exception erro){
			MensagemAlerta.alerta("Banco de Dados", "Não foi possível criar ou abrir o banco!"+erro, this);
		}
		
	}
	private void FecharBanco(){
		//
		try{
			BancoDados.close();
		}catch(Exception erro){
			MensagemAlerta.alerta("Banco de Dados", "Não foi possível fechar o banco!"+erro, this);
		}
		
	}
		
	public void Gravar(int posicao) {
		AbrirBanco();
		//
		if(posicao == -1){
			try{
				BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
				String SQL = "INSERT INTO tabCadastroPessoa (nomePessoa, senhaPessoa) VALUES ('"+EditNome.getText().toString()+"', '"+EditSenha.getText().toString()+"')";
				BancoDados.execSQL(SQL);
				MensagemAlerta.alerta("Banco de Dados", "Registro inserido!",this);
			}catch(Exception erro){
				MensagemAlerta.alerta("Banco de Dados", "Não foi possível inserir o registro!"+erro,this);
			}
			finally{
				FecharBanco();
			}
		}
		else{
			AlterarRegistro(posicao);
		}
		
		FecharBanco();
	}
	public void Alterar(){
		AbrirBanco();
		//
		
		FecharBanco();
	}
	public void Apagar(){
		AbrirBanco();
		//
		
		FecharBanco();
	}

}
