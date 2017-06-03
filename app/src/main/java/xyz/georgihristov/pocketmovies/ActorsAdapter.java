package xyz.georgihristov.pocketmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>{
    public List<Cast> castList;
    private Context context;




    public ActorsAdapter(Context context, List<Cast> castList) {
        this.castList = castList;
        this.context = context;
    }

    @Override
    public ActorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actors_list_item, parent, false);
        ActorsViewHolder viewHolder = new ActorsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActorsViewHolder holder, int position) {
            holder.bindActor(castList.get(position));

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    class ActorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView1;

        private Cast actor;

        public ActorsViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);


        }
        public void bindActor(Cast actor){
            this.actor = actor;
            Picasso.with(context).load(Api.GET_CAST_PICTURE+actor.getProfilePath()).into(imageView1);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, actor.getName() +"\n" + "In the Role of: "
                    +actor.getCharacter(), Toast.LENGTH_SHORT).show();
        }


    }


}
