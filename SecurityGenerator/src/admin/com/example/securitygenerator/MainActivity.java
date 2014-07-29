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
import admin.com.example.securitygenerator.MensagemAlerta;



public class MainActivity extends Activity {

	EditText EditNome, EditSenha;
	Button Salvar;
	Button Buscar;
	Button Apagar;
	//usado pelo criabanco
	String nomeBanco = "Cadastro";
	SQLiteDatabase BancoDados = null;
	Cursor cursor;
	ListView MostraDados;
	SimpleCursorAdapter AdaptaLista;
	public static final String KEY_NOME_PESSOA = "nomePessoa";
	static int id = -1;
	static String Aid;
	//MensagemAlerta mensagem;
	//public static final String KEY_NOME_PESSOA = "_id, nomePessoa, senhaPessoa";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//mensagem = new MensagemAlerta();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EditNome = (EditText) findViewById(R.id.idnome);
		EditSenha = (EditText) findViewById(R.id.idsenha);
		
		Salvar = (Button) findViewById(R.id.BtnSalvar);
		Apagar = (Button) findViewById(R.id.BtnApagar);
		Apagar.setVisibility(View.INVISIBLE);
		//Buscar = (Button) findViewById(R.id.BtnMostrar);
		CriaBanco();
		//MostraDados = (ListView) findViewById(R.id.MostraDados1);
		//GravaBanco();
		CarregaDado();
		btnSalvarDados();
		btnApagarDados();
		
		
		//Buscar id do item clicado  (esta passando indice ao invez de id)
		MostraDados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int Posicao,long arg3) {
								
				Long value = new Long(arg3);
				id = value.intValue();
				SetarDados(id);			
			}
		});
	}
	
	//Mostra os dados referentes ao nome clicado no listview
	public void SetarDados(int Posicao){
		try {
			Salvar.setText("Alterar");
			Apagar.setVisibility(View.VISIBLE);
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			Cursor c = BancoDados.rawQuery("SELECT * FROM tabCadastroPessoa WHERE _id = '"+Posicao+"'", null);
			while(c.moveToNext()){
				EditNome.setText(c.getString(c.getColumnIndex("nomePessoa")));
				EditSenha.setText(c.getString(c.getColumnIndex("senhaPessoa")));
			}
			
		} catch (Exception erro) {
			MensagemAlerta.alerta("Erro!", "Não foi possivel realizar a operação!!!",this);
		}
	}
	//Altera o registro selecionado de acordo com a ID recebida
	private boolean AlterarRegistro(int id){
		try {
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			String sql = "UPDATE tabCadastroPessoa SET nomePessoa = '"+EditNome.getText().toString()+"', "
			+ "senhaPessoa = '"+EditSenha.getText().toString()+"' WHERE _id = '"+id+"'";
			BancoDados.execSQL(sql);
			MensagemAlerta.alerta("Exito!", "Dados alterados com sucesso!",this);
			return true;
		} catch (Exception erro) {
			MensagemAlerta.alerta("Erro!!!", "Não foi possivel alterar a seleção!!!"+erro,this);
			return false;
		}
		finally{
			BancoDados.close();
		}
	}
	
	//Cria o banco de dados caso não exista
	public void CriaBanco(){
		try{
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			String SQL = "CREATE TABLE IF NOT EXISTS tabCadastroPessoa(_id INTEGER PRIMARY KEY, nomePessoa TEXT, senhaPessoa TEXT)";
			BancoDados.execSQL(SQL);
			//MensagemAlerta.alerta("Banco de Dados", "Banco criado com sucesso!");
		}catch(Exception erro){
			MensagemAlerta.alerta("Banco de Dados", "Não foi possível criar o banco!"+erro,this);
		}
		finally{
			BancoDados.close();
		}
	}
	
	//Grava as informações no banco
	public void GravaBanco(int posicao){
		
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
				BancoDados.close();
			}
		}
		else{
			AlterarRegistro(posicao);
		}
		}
	
	//apaga o dado do banco (incompleto)
	public void ApagaBanco(int posicao){
		
		
			try{
				
				
				BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
				String SQL = "DELETE FROM tabCadastroPessoa WHERE _id = '"+posicao+"'";
				BancoDados.execSQL(SQL);
				MensagemAlerta.alerta("Banco de Dados", "Registro apagado!",this);
			}catch(Exception erro){
				MensagemAlerta.alerta("Banco de Dados", "Não foi possível apagar o registro!"+erro,this);
			}
			finally{
				BancoDados.close();
			}
		
		
		}	
	
	//
	private boolean VerificaRegistro(){
		try{
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			//cursor = BancoDados.rawQuery("Select from tabCadastroPessoa", null);
			cursor = BancoDados.rawQuery("Select * from tabCadastroPessoa", null);
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				return true;
			}
			else
				return false;
		}catch(Exception erro){
			MensagemAlerta.alerta("Banco de Dados", "Não foi possível buscar os registros!"+erro,this);
			return false;
		}
		finally{
			BancoDados.close();
		}
	}
	
	//tenta carregar dados salvos no banco
	public void CarregaDado(){
		MostraDados = (ListView) findViewById(R.id.MostraDados1);
		if (VerificaRegistro()){
			String[] Coluna = new String [] {KEY_NOME_PESSOA};
			
			AdaptaLista = new SimpleCursorAdapter(this, R.layout.carregadados, cursor, Coluna, new int[] {R.id.carregaDado});
			MostraDados.setAdapter(AdaptaLista);
		}
		else{
			//String[] Coluna = new String [] {KEY_NOME_PESSOA};
			//AdaptaLista = new SimpleCursorAdapter(this, R.layout.carregadados, cursor, null, new int[] {R.id.carregaDado});
			MostraDados.setAdapter(null);
			MensagemAlerta.alerta("Banco de Dados", "Nenhum dado encontrado!",this);
		}
	}
	
	//botão salvar
	public void btnSalvarDados(){
		Salvar.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				if(EditNome.getText().toString().equals("") || EditSenha.getText().toString().equals("")){
					//MensagemAlerta.alerta("Violação", "Todos os campos devem ser preenchidos!",this);
					MensagemAlerta.alerta("Violação", "Todos os campos devem ser preenchidos!",null);
					return;
				}
				GravaBanco(id);
				CarregaDado();
				
				id = -1;
				Salvar.setText("Salvar");
				Apagar.setVisibility(View.INVISIBLE);
				EditNome.setText("");
				EditSenha.setText("");
				//MensagemAlerta("Banco de Dados", "Registro salvo com sucesso!");
				
			}
		});
	}
	//Btn mostrar os dados
	//Botão apagar dados selecionados 
	public void btnApagarDados(){
		Apagar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ApagaBanco(id);
				CarregaDado();
				
				id = -1;
				Salvar.setText("Salvar");
				Apagar.setVisibility(View.INVISIBLE);
				EditNome.setText("");
				EditSenha.setText("");
				
				
			}
		});
		
	}

	//Alerta// separado em uma classe
//	public void MensagemAlerta(String TituloAlerta, String MensagemAlerta){
//		AlertDialog.Builder Mensagem = new AlertDialog.Builder(this);
//		Mensagem.setTitle(TituloAlerta);
//		Mensagem.setMessage(MensagemAlerta);
//		Mensagem.setNeutralButton("Ok", null);
//		Mensagem.show();
//	}
	

	@Override
 	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
