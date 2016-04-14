package com.wu.signalchannelcharacterization;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.List;

/**
 * Created by wu on 3/14/16.
 */
public class LTEListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private final static String TAG = "LTEFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,0);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    private void updateUI() {
        LTEList lteList = LTEList.get(getActivity());
        List<LTE> ltes = lteList.getLTEs();

        if (mAdapter == null) {
            mAdapter = new Adapter(ltes);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private LTE mLTE;
        private TextView mPci;
        private TextView mDbm;
        private TextView mAsuLevel;
        private TextView mTimingAdvance;


        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPci            = (TextView) itemView.findViewById(R.id.list_pci_text_view);
            mDbm            = (TextView) itemView.findViewById(R.id.list_dbm_text_view);
            mAsuLevel       = (TextView) itemView.findViewById(R.id.list_asulevel_text_view);
            mTimingAdvance  = (TextView) itemView.findViewById(R.id.list_timing_advance_text_view);
        }

        public void bindLTE(LTE lte) {
            mLTE = lte;
            mPci.setText(getResources().getString(R.string.PCI)
                    .concat(String.valueOf(lte.getmPci())));
            mDbm.setText(getResources().getString(R.string.DBM)
                    .concat(String.valueOf(lte.getmDbm())));
            mAsuLevel.setText(getResources().getString(R.string.ASU)
                    .concat(String.valueOf(lte.getmAsuLevel())));
            mTimingAdvance.setText(String.valueOf(getResources().getString(R.string.TAD)
                    .concat(String.valueOf(lte.getmTimingAdvance()))));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    "Pci No." + mLTE.getmPci() + " is chosen !", Toast.LENGTH_SHORT)
                    .show();
            Intent i = LocationActivity.newIntent(getActivity(),mLTE.getmPci());
            startActivity(i);
        }
    }


    private class Adapter extends RecyclerView.Adapter<Holder> {

        private List<LTE> mLTEs;
        public Adapter(List<LTE> LTEs) {
            mLTEs = LTEs;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_lte, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder,int position) {
            LTE lte = mLTEs.get(position);
            holder.bindLTE(lte);
        }

        @Override
        public int getItemCount() {
            return mLTEs.size();
        }
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
            handler.postDelayed(this,500);
        }
    };

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}