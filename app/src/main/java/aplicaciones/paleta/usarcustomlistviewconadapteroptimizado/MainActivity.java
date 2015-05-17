package aplicaciones.paleta.usarcustomlistviewconadapteroptimizado;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


     /*
       1 Almacenar la data el algun lugar [titulos, descripciones e imagenes]
       2 Definir la estructura de una simple row (fila) para tu listView en un layout aparte
       3 Crear tu custom adapter en el cual se pondra la data para cada row en el View

        4 Crear una clase que herede (extends) de un BaseAdapter e implemente (implements) todos sus metodos
        5 Mantener algunos arreglos dentro de tu clase BaseAdapter la cual contendra todos los datos (titulos+descripciones+imagenes)
        6 Usar el metodo getView para llenar la data desde tu arreglo interior de la estructura de una simple fila para cada fila
     */
    ListView miLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miLista=(ListView)findViewById(R.id.miLista);
        miLista.setAdapter(new miAdaptadorPersonalizado(this));
    }


    class miFilaSimple{
        String titulo;
        String descripcion;
        int imagen;

        /*Constructor*/
        miFilaSimple(String titulo,String descripcion,int imagen){
            this.titulo=titulo;
            this.descripcion=descripcion;
            this.imagen=imagen;
        }

    }

    class MiViewHolder{
        ImageView miImagen;
        TextView miTitulo;
        TextView miDescripcion;
        MiViewHolder(View v){
            miImagen=(ImageView) v.findViewById(R.id.imagenZelda);
            miTitulo=(TextView) v.findViewById(R.id.titulo);
            miDescripcion=(TextView) v.findViewById(R.id.descripcion);
        }
    }

    class miAdaptadorPersonalizado extends BaseAdapter{

        /* SECCION Declaracion de variables*/
        ArrayList<miFilaSimple> lista;
        Context context;

        /*CONSTRUCTOR*/

        miAdaptadorPersonalizado(Context c){
            context=c;
            lista= new ArrayList<miFilaSimple>();

            /*
                1- Obtener los titulos, descripciones e imagenes desde un xml (para este caso)
                2- agrupar cada titulo, relacionarlo con la descripcion y su imagen en un objeto miFilaSimple
                3- poner los miFilaSimple simple  dentro del array list
             */

            Resources res= c.getResources();
            String[] titulos=res.getStringArray(R.array.titulos);
            String[] descripciones= res.getStringArray(R.array.descripciones);
            int[] images={R.drawable.link,R.drawable.zelda,R.drawable.sheik,R.drawable.ganondorf,R.drawable.girahim,R.drawable.toonlink,R.drawable.toonzelda};
            for(int i=0;i<images.length;i++){
               lista.add( new miFilaSimple(titulos[i],descripciones[i],images[i]));
            }
        }

        /* SECCION DE METODOS pertenecientes al BaseAdapter
         getCount -cuenta cuantos elementos tiene
         getItem - obtiene el objeto segun la posicion
         getItemId - obtiene el id segun la posicion
         getView - obtiene la View
        * */
        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int position) {
            return lista.get(position); //regresa el objeto en esa posicion
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*Aqui la magia*/

            View row=convertView;
            MiViewHolder holder=null;
            if(row==null){
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(R.layout.mi_fila,parent,false); //contiene una referencia al RelativeLayout
                holder= new MiViewHolder(row);
                row.setTag(holder);
                Log.d("ZELDA", "Creando una nueva fila");
            }
            else{
                holder=  (MiViewHolder) row.getTag();
                Log.d("ZELDA", "Reciclando cosas");
            }
            miFilaSimple temp=lista.get(position);
            holder.miTitulo.setText(temp.titulo);
            holder.miDescripcion.setText(temp.descripcion);
            holder.miImagen.setImageResource(temp.imagen);



            /* VERSION SIN OPTIMIZACION Inicializar el layout inlfater */
             /*
               1- Obtener el root view (seria el relativeLayout de buestro mi_fila.xml
               2- usar el root view para encontrar las otras views
                3- Establecer los valores
            * */
            /*
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.mi_fila,parent,false); //contiene una referencia al RelativeLayout
            TextView titulo=(TextView)row.findViewById(R.id.titulo);
            TextView descripcion=(TextView)row.findViewById(R.id.descripcion);
            ImageView imagen=(ImageView) row.findViewById(R.id.imagenZelda);

            miFilaSimple temp=lista.get(position);
            titulo.setText(temp.titulo);
            descripcion.setText(temp.descripcion);
            imagen.setImageResource(temp.imagen);
            */



             return row;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
