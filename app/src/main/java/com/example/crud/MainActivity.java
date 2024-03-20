package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText edtId;
    private EditText edtNome;
    private EditText edtPreco;
    private EditText edtVolume;
    private EditText edtSabor;
    private Button btnCadastrar;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnVoltar;
    private Banco banco;
    private ListView lvwBebidas;
    List<Bebida> dadosBebidas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.edtId);
        edtId.setEnabled(false);
        edtNome = findViewById(R.id.edtNome);
        edtPreco = findViewById(R.id.edtPreco);
        edtVolume = findViewById(R.id.edtVolume);
        edtSabor = findViewById(R.id.edtSabor);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnUpdate.setEnabled(false);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnDelete.setEnabled(false);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);
        lvwBebidas = findViewById(R.id.lvwBebidas);
        lvwBebidas.setOnItemClickListener(this);

        banco = new Banco(getApplicationContext());
        banco = new Banco(this);
        BebidaDAO bebidadao = bebidadao = new BebidaDAO(this.banco);
        List<Bebida> bebidas = bebidadao.buscarTodos();
        for(int i=0; i< bebidas.size(); i++) {
            System.out.println(bebidas.get(i).getId());
            System.out.println(bebidas.get(i).getNome());
            System.out.println(bebidas.get(i).getPreco());
            System.out.println(bebidas.get(i).getVolume());
            System.out.println(bebidas.get(i).getSabor());
        }
        preencheListView();

    }

    public void preencheListView(){
        BebidaDAO bebidaDAO = new BebidaDAO(banco);
        dadosBebidas = bebidaDAO.buscarTodos();
        String[] arrayBebidas = new String[dadosBebidas.size()];
        for (int i=0; i<dadosBebidas.size(); i++) {
            Bebida bebida = dadosBebidas.get(i);
            arrayBebidas[i] = bebida.getId()+ ":" + bebida.getNome()+ ":" + bebida.getPreco()+ ":" + bebida.getVolume()+ ":" + bebida.getSabor();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayBebidas);
        lvwBebidas.setAdapter(adaptador);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnCadastrar) {
            //salvar as informacoes BD
            Bebida b= new Bebida();
            BebidaDAO bebidadao = bebidadao = new BebidaDAO(this.banco);
            b.setNome(edtNome.getText().toString());
            b.setPreco(Integer.parseInt(edtPreco.getText().toString()));
            b.setVolume(edtVolume.getText().toString());
            b.setSabor(edtSabor.getText().toString());
            bebidadao.save(b);
            Toast.makeText(getApplicationContext(),"Bebida cadastrada com sucesso", Toast.LENGTH_LONG).show();
            edtId.getText().clear();
            edtNome.getText().clear();
            edtPreco.getText().clear();
            edtVolume.getText().clear();
            edtSabor.getText().clear();
        }
        else if (view.getId()==R.id.btnUpdate) {
            Bebida b= new Bebida();
            BebidaDAO bebidadao = bebidadao = new BebidaDAO(this.banco);
            b.setId(Integer.parseInt(edtId.getText().toString()));
            b.setNome(edtNome.getText().toString());
            b.setPreco(Integer.parseInt(edtPreco.getText().toString()));
            b.setVolume(edtVolume.getText().toString());
            b.setSabor(edtSabor.getText().toString());
            bebidadao.update(b);
            Toast.makeText(getApplicationContext(),"Bebida atualizada com sucesso", Toast.LENGTH_LONG).show();
            edtId.getText().clear();
            edtNome.getText().clear();
            edtPreco.getText().clear();
            edtVolume.getText().clear();
            edtSabor.getText().clear();
        }
        else if (view.getId()==R.id.btnDelete) {
            Bebida b= new Bebida();
            BebidaDAO bebidadao = bebidadao = new BebidaDAO(this.banco);
            b.setId(Integer.parseInt(edtId.getText().toString()));
            bebidadao.delete(b);
            Toast.makeText(getApplicationContext(),"Bebida deletada com sucesso", Toast.LENGTH_LONG).show();
            edtId.getText().clear();
            edtNome.getText().clear();
            edtPreco.getText().clear();
            edtVolume.getText().clear();
            edtSabor.getText().clear();
        }
        else if (view.getId()==R.id.btnVoltar) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        preencheListView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String itemSelecionado = (String) adapterView.getItemAtPosition(i);
        String[] textoSeparado = itemSelecionado.split(":");

        edtId = findViewById(R.id.edtId);
        edtNome = findViewById(R.id.edtNome); // aponta para o campo Nome
        edtPreco = findViewById(R.id.edtPreco); // aponta para o campo Preco
        edtVolume = findViewById(R.id.edtVolume); // aponta para o campo Volume
        edtSabor = findViewById(R.id.edtSabor); // aponta para o campo Sabor
        edtId.setText(textoSeparado[0].trim());
        edtNome.setText(textoSeparado[1].trim());//trim tira os espaços
        edtPreco.setText(textoSeparado[2].trim());//trim tira os espaços
        edtVolume.setText(textoSeparado[3].trim());//trim tira os espaços
        edtSabor.setText(textoSeparado[4].trim());//trim tira os espaços

        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnCadastrar.setEnabled(false);
    }
}