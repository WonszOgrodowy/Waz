package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.MenuItem;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class gameplay extends Activity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_game);

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new OpenGLRenderer());
        setContentView(view);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class OpenGLRenderer implements android.opengl.GLSurfaceView.Renderer
{

    private final float a = 1f;
    private float[] tablicaWerteksow =
            {
                    //tylnia
                    a,-a,-a,
                    -a,-a,-a,
                    a,a,-a,
                    -a,a,-a,
                    //przednia
                    -a,-a,a,
                    a,-a,a,
                    -a,a,a,
                    a,a,a,

                    //prawa
                    a,-a,a,
                    a,-a,-a,
                    a,a,a,
                    a,a,-a,
                    //lewa
                    -a,-a,-a,
                    -a,-a,a,
                    -a,a,-a,
                    -a,a,a,

                    //gorna
                    -a,a,a,
                    a,a,a,
                    -a,a,-a,
                    a,a,-a,
                    //dolna
                    -a,-a,-a,
                    a,-a,-a,
                    -a,-a,a,
                    a,-a,a
            };

    private FloatBuffer buforWerteksow_Polozenia;

    private void inicjujBuforWerteksow()
    {
        ByteBuffer vbb = ByteBuffer.allocateDirect(tablicaWerteksow.length * 4); //float = 4 bajty
        vbb.order(ByteOrder.nativeOrder());
        buforWerteksow_Polozenia = vbb.asFloatBuffer(); //konwersja z bajtów do float
        buforWerteksow_Polozenia.put(tablicaWerteksow); //kopiowanie danych do bufora
        buforWerteksow_Polozenia.position(0);           //rewind
    }

    float kolory[][] =
            {
                    {1f,0f,0f,1f}, //tylnia
                    {1f,0f,0f,1f}, //przednia
                    {0f,1f,0f,1f}, //prawa
                    {0f,1f,0f,1f}, //lewa
                    {0f,0f,1f,1f}, //gorna
                    {0f,0f,1f,1f} //dolna
            };

    private void rysujSzescian(GL10 gl,float krawedz, boolean kolor)
    {
        gl.glFrontFace(GL10.GL_CCW);    //przednie sciany wskazane przez nawijanie przeciwne do ruchu wskazówek zegara
        gl.glEnable(GL10.GL_CULL_FACE); //usuwanie tylnich powierzchni
        gl.glCullFace(GL10.GL_BACK);

        if(krawedz!=1.0f) gl.glPushMatrix(); //zapamietaj macierz model-widok (wloz na stos macierzy)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, buforWerteksow_Polozenia);

        if(!kolor) gl.glColor4f(1f, 1f, 1f, 1f);

        for(int i=0;i<6;++i)
        {
            if(kolor) gl.glColor4f(kolory[i][0], kolory[i][1], kolory[i][2], kolory[i][3]);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
        }

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }


    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glShadeModel(GL10.GL_SMOOTH);

        //ustawienia testu glebii (takie, jak domylne)
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glDisable(GL10.GL_DITHER);
        inicjujBuforWerteksow();
    }

    private void ustawienieSceny(GL10 gl,int szer,int wys)
    {
        gl.glViewport(0, 0, szer, wys);

        gl.glMatrixMode(GL10.GL_PROJECTION); //przełšczenie na macierz projekcji
        gl.glLoadIdentity();
        //left,right,bottom,top,znear,zfar (clipping)
        //float wsp=wys/(float)szer;
        //gl.glFrustumf(-0.1f, 0.1f, wsp*-0.1f, wsp*0.1f, 0.3f, 100.0f);
        GLU.gluPerspective(gl, 45.0f, (float) szer / (float) wys, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW); //powrót do macierzy widoku modelu
        gl.glLoadIdentity();
        GLU.gluLookAt(
                gl,
                0, 0, 7.5f, //polozenie kamery
                0, 0, 0, //cel
                0, 1, 0); //polaryzacja
    }


    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glPushMatrix(); //zapamietaj macierz model-widok (wloz na stos macierzy)
        rysujSzescian(gl,1.0f,true);
        gl.glPopMatrix(); //zdejmij ze stosu macierzy = odtworz zapamietany stan
    }

    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        if (height == 0) height = 1;
        ustawienieSceny(gl,width,height);
    }
}
