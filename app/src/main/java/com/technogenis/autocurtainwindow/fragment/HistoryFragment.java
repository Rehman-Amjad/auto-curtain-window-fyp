package com.technogenis.autocurtainwindow.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.technogenis.autocurtainwindow.R;
import com.technogenis.autocurtainwindow.adapter.HistoryAdapter;
import com.technogenis.autocurtainwindow.model.HistoryModel;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HistoryAdapter mHistoryAdapter;
    private List<HistoryModel> mDataList = new ArrayList<>();
    private Button btnDeleteAll;
    private DatabaseReference historyRef;
    private ChildEventListener historyChildEventListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase database reference
        historyRef = FirebaseDatabase.getInstance().getReference("History");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        initUI(view);
        setupFirebaseListener();

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllHistoryData();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the listener to avoid memory leaks
        if (historyChildEventListener != null) {
            historyRef.removeEventListener(historyChildEventListener);
        }
    }

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        btnDeleteAll = view.findViewById(R.id.btnDeleteAll);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new HistoryAdapter(getActivity(), mDataList);
        recyclerView.setAdapter(mHistoryAdapter);
    }

    private void setupFirebaseListener() {
        historyChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                HistoryModel history = snapshot.getValue(HistoryModel.class);
                if (history != null) {
                    mDataList.add(history);
                    mHistoryAdapter.notifyItemInserted(mDataList.size() - 1);
                }
                // Hide progress bar and show RecyclerView once data is loaded
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle data changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                HistoryModel history = snapshot.getValue(HistoryModel.class);
                if (history != null) {
                    int index = findItemIndex(history);
                    if (index != -1) {
                        mDataList.remove(index);
                        mHistoryAdapter.notifyItemRemoved(index);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle data moves if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors if necessary
                // Hide progress bar in case of error
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        };

        historyRef.addChildEventListener(historyChildEventListener);
    }

    private int findItemIndex(HistoryModel history) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getId().equals(history.getId())) {
                return i;
            }
        }
        return -1;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteAllHistoryData() {
        // Show a confirmation toast before deletion
        Toast.makeText(getActivity(), "Deleting all history...", Toast.LENGTH_SHORT).show();

        // Delete all data under "History" in Firebase
        historyRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mDataList.clear();
                mHistoryAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "All history deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to delete history", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
