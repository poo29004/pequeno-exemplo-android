package br.edu.ifsc.sj.poo.pequenosexemplos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Convenções de nomenclatura de atributos em Android
    // https://source.android.com/setup/contribute/code-style#follow-field-naming-conventions

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayerSoundEffect;
    private boolean mTocando = false;

    // As músicas foram obtidas em https://patrickdearteaga.com/arcade-music/
    private final int[] MUSICAS = {R.raw.battleship, R.raw.chiptronical, R.raw.match, R.raw.puzzle};
    private final CharSequence[] LISTA_DE_MUSICAS = {"Battleship", "Chiptronical", "Match", "Puzzle"};

    private int mMusicaEscolhida;
    private boolean mCorImagem = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMusicaEscolhida = R.raw.puzzle;
        this.mCorImagem = false;
    }

    public void musicaFundo(View view) {

        Button b1 = findViewById(R.id.b1);

        if (!this.mTocando){
            mMediaPlayer = MediaPlayer.create(this, this.mMusicaEscolhida);
            mMediaPlayer.start();
            this.mTocando = true;
            b1.setText(R.string.parar);

        }else{
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
            this.mTocando = false;

            b1.setText(R.string.musica);
        }
    }


    public void buzina(View view){
        // Para efeitos sonoros talvez a classe SoundPool seja mais adequada
        // para simplificar, foi feito uso da MediaPlayer

        if (mMediaPlayerSoundEffect != null){
            mMediaPlayerSoundEffect.stop();
            mMediaPlayerSoundEffect.release();
        }

        mMediaPlayerSoundEffect = MediaPlayer.create(this,R.raw.horn);
        mMediaPlayerSoundEffect.start();
    }


    public void pararMusica(){
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            this.mTocando = false;
        }
        if (mMediaPlayerSoundEffect != null){
            mMediaPlayerSoundEffect.release();
            mMediaPlayerSoundEffect = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.pararMusica();
    }

    public void chamarDialogo(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Adicionando mensagem e título
        builder.setMessage(R.string.mensagem)
                .setTitle(R.string.titulo_dialogo);

        // Adicionando botões
        builder.setPositiveButton(R.string.ok, (dialog, id) -> Toast.makeText(getApplicationContext(), "Clicou no SIM", Toast.LENGTH_LONG).show());
        builder.setNegativeButton(R.string.cancel, null);


        builder.create().show();
    }


    public void escolhaMusica(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.escolha_musica)
                .setItems(this.LISTA_DE_MUSICAS, (dialog, qual) -> {

                    this.mMusicaEscolhida = MUSICAS[qual];

                    this.pararMusica();

                    this.musicaFundo(view);

                });

        builder.create().show();

    }

    public void trocaImagem(View view) {
        ImageView imageView = (ImageView) view;

        if (mCorImagem){
            imageView.setImageResource(R.drawable.ic_race);

        }else{
            imageView.setImageResource(R.drawable.ic_race_blue);
        }

        mCorImagem = !mCorImagem;


    }
}
