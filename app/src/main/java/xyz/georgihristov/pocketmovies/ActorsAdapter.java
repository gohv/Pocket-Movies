package xyz.georgihristov.pocketmovies;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder> {
    public final List<Cast> castList;
    private final Context context;
    private View view;

    public ActorsAdapter(Context context,View view, List<Cast> castList) {
        this.castList = castList;
        this.context = context;
        this.view = view;
    }

    @Override
    public ActorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actors_list_item, parent, false);

        return new ActorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActorsViewHolder holder, int position) {
        holder.bindActor(castList.get(position));

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    class ActorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView1;

        private Cast actor;

        public ActorsViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);


        }

        public void bindActor(Cast actor) {
            this.actor = actor;
            Picasso.with(context).load(Api.GET_CAST_PICTURE + actor.getProfilePath()).into(imageView1);
        }

        @Override
        public void onClick(View v) {

            createSnackBar(view,actor);

        }


    }

    private void createSnackBar(View v, final Cast actor){
        String displaySnack = actor.getName() + "\n" + "In the Role of: "
                + actor.getCharacter();
        Snackbar bar = Snackbar.make(v, displaySnack, Snackbar.LENGTH_LONG)
                .setAction("Details", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent = new Intent(context,PersonDetails.class);
            intent.putExtra("PERSONID",actor.getId());
            context.startActivity(intent);
                    }
                });

        bar.show();
    }

}
