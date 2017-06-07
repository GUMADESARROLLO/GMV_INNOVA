package com.guma.desarrollo.gmv.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.gmv.Pedido;
import com.guma.desarrollo.gmv.R;

import java.util.List;

/**
 * Created by maryan.espinoza on 23/02/2017.
 */

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.PedidosViewHolder>{

    List<Pedido> pedidos;

    public AdapterPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pedidos,parent,false);
        PedidosViewHolder holder = new PedidosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, int position) {
        final Pedido pdd = pedidos.get(position);
        String st = "";

        switch (pdd.getEstado()){
            case "0":
                st ="PENDIENTE";
                break;
            case "1":
                st ="INGRESADO";
                break;
        }
        holder.name.setText(pdd.getCliente());
        holder.codigo.setText(pdd.getCliente());
        holder.estado.setText(st);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), pdd.getCliente(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class PedidosViewHolder extends RecyclerView.ViewHolder{
        TextView name,codigo,estado;
        public PedidosViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_id_pedido);
            codigo = (TextView) itemView.findViewById(R.id.txt_id_cliente);
            estado = (TextView) itemView.findViewById(R.id.txt_id_estado);

        }
    }

}
