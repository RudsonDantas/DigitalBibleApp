package org.armstrong.ika.digitalbibleapp.Completed;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.armstrong.ika.digitalbibleapp.Common.DividerLineDecoration;
import org.armstrong.ika.digitalbibleapp.Common.RecyclerTouchListener;
import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedEntities;
import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedRepository;
import org.armstrong.ika.digitalbibleapp.CompletedDb.CompletedViewModel;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import java.util.List;

public class CompletedFragment extends Fragment {

    protected PreferenceProvider preferenceProvider;
    protected CompletedRepository completedRepository;
    protected CompletedViewModel completedViewModel;

    private RecyclerView recyclerView;
    private CompletedFragmentAdapter completedFragmentAdapter;

    private List<CompletedEntities> values;

    public static CompletedFragment newInstance() {

        CompletedFragment completedFragment = new CompletedFragment();

        return completedFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());

        completedRepository = new CompletedRepository(getContext());

        completedViewModel = ViewModelProviders.of(getActivity())
                .get(CompletedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.completed_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.CompletedRecyclerView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        completedFragmentAdapter = new CompletedFragmentAdapter(preferenceProvider.gettextSizeVar());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(completedFragmentAdapter);

        completedViewModel.getAllCompletedByTime().observe(getActivity(), new Observer<List<CompletedEntities>>() {
            @Override
            public void onChanged(List<CompletedEntities> completedEntities) {
                completedFragmentAdapter.setValues(completedEntities);
                values = completedEntities;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                CompletedEntities completedEntities = values.get(position);
                //Log.e("logg", "onClick: comleted id " + completedEntities.getId());

                StringBuilder sb = new StringBuilder();
                sb.append(completedEntities.getAbbreviation());
                sb.append(" ");
                sb.append(completedEntities.getBookname());
                sb.append("\n");
                sb.append(completedEntities.getTime());

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Delete?")
                        .setMessage(sb.toString())
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                if (completedRepository.deleteCompletedById(completedEntities.getId()) != -1) {
                                    Utils.makeToast(getContext(), completedEntities.getAbbreviation() + " " + completedEntities.getBookname() + " deleted");
                                }
                            }
                        }).show();

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing
            }

        }));

    }


}
