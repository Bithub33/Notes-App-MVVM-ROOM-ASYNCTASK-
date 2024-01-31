package com.example.archnotes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ListAdapter<Notes,NotesAdapter.NoteHolder> {

    private onItemClickListener listener;

    protected NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Notes> DIFF_CALLBACK = new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(
                    newItem.getDescription()) && oldItem.getPriority() == newItem.getPriority();
        }
    };

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private CardView cardView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.lay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClicked(getItem(getAdapterPosition()));
                    }
                }
            });

        }
    }

    public interface onItemClickListener {
        void onItemClicked(Notes notes);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public Notes getNotePos(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Notes currentNotes = getItem(position);
        holder.title.setText(currentNotes.getTitle());
        holder.description.setText(currentNotes.getDescription());
        
    }

}
