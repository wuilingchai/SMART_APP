package com.bignerdranch.android.smart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.smart.database.SmartBaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;


public class SmartListFragment extends Fragment{

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mSmartRecyclerView;
    private SmartAdapter mAdapter;
    private boolean mSubtitleVisible;

    String newDate;
    String newTime;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_smart_list, container, false);

        mSmartRecyclerView = (RecyclerView) view
                .findViewById(R.id.smart_recycler_view);
        mSmartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_smart_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.new_goal:
                Smart smart = new Smart();
                SmartLab.get(getActivity()).addGoal(smart);
                Intent intent = SmartPagerActivity
                        .newIntent(getActivity(),smart.getId());
                startActivity(intent);
                Toast.makeText(getContext(),"New goal added!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

                       builder.setTitle("About me");
                       builder.setMessage("Specific = Specify your goal" + "\n" + "\n"+
                                "Measurable = Show evidence to prove your progress" + "\n" + "\n" +
                                "Attainable = Ensure that the goal is achievable" + "\n" + "\n" +
                                "Relevant = Aligned the values and objective with the goal " + "\n" + "\n" +
                                "Time-based = Set a realistic end-date for the motivation and prioritization");
                        builder.setPositiveButton("OK", null);
                        builder.show();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateSubtitle(){
        SmartLab smartLab = SmartLab.get(getActivity());
        int goalCount = smartLab.getSmart().size();
        String subtitle = getString(R.string.subtitle_format, goalCount);

        if (!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI(){
        SmartLab smartLab = SmartLab.get(getActivity());
        List<Smart> smart = smartLab.getSmart();

        if (mAdapter == null){
            mAdapter = new SmartAdapter(smart);
            mSmartRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setSmart(smart);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class SmartHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private Smart mSmart;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private ImageView mCompletedImageView;
        ItemLongClickListener mItemLongClickListener;



        public SmartHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_smart,parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


            mTitleTextView = (TextView) itemView.findViewById(R.id.smart_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.smart_date);
            mTimeTextView = (TextView) itemView.findViewById(R.id.smart_time);
            mCompletedImageView = (ImageView) itemView.findViewById(R.id.smart_completed);

        }


        public void bind(Smart smart){
            mSmart = smart;
            mTitleTextView.setText(mSmart.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd'/'MMM'/'yyyy");
            newDate = simpleDateFormat.format(mSmart.getDate());
            mDateTextView.setText(newDate);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("h:mm a");
            newTime = simpleDateFormat1.format(mSmart.getTime());
            mTimeTextView.setText(newTime);
            mCompletedImageView.setVisibility(smart.isCompleted() ? View.VISIBLE : View.GONE);
        }

        public void setItemLongClickListener(ItemLongClickListener item){
            this.mItemLongClickListener = item;
        }

        @Override
        public void onClick(View view) {
            Intent intent = SmartPagerActivity.newIntent(getActivity(), mSmart.getId());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            this.mItemLongClickListener.onItemLongClick(v, getLayoutPosition());
            return false;
        }
    }

    private class SmartAdapter extends RecyclerView.Adapter<SmartHolder>{
        private List<Smart> mSmart;

        public SmartAdapter (List<Smart> smart){
            mSmart = smart;
        }

        @Override
        public SmartHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SmartHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (final SmartHolder holder, int position){
            Smart smart = mSmart.get(position);
            holder.bind(smart);
            holder.setItemLongClickListener(new ItemLongClickListener() {
                @Override
                public void onItemLongClick(View v, final int position) {
                    Toast.makeText(getContext(), "OnLongClick called at position" + position, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to delete this goal?");
                    builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Goal Deleted",
                                    Toast.LENGTH_LONG).show();
                            SmartBaseHelper helperdelete = new SmartBaseHelper(holder.itemView.getContext());
                            helperdelete.deleteGoal(mSmart.get(position).getId());
                            mSmart.remove(position);
                            notifyItemRemoved(position);
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();


                }
            });
        }



        @Override
        public int getItemCount(){
            return mSmart.size();
        }

        public void setSmart (List<Smart> smart){
            mSmart = smart;
        }


    }


}
