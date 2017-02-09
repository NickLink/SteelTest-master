package link.nick.com.steeltest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import link.nick.com.steeltest.R;
import link.nick.com.steeltest.domain.GithubRepo;

/**
 * Created by Nick on 06.02.2017.
 */

public class ReposListAdapter extends BaseAdapter {
    private Context context;
    private List<GithubRepo> reposList;
    private LayoutInflater inflater;

    static class ViewHolder {
        public TextView repoName;
        public TextView repoDesc;
    }

    public ReposListAdapter(Context context, List<GithubRepo> reposList){
        this.context = context;
        this.reposList = reposList;
    }

    public List<GithubRepo> getReposList() {
        return reposList;
    }

    public void setReposList(List<GithubRepo> reposList) {
        this.reposList = reposList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reposList.size();
    }

    @Override
    public GithubRepo getItem(int i) {
        return reposList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
            ViewHolder holder = new ViewHolder();
            holder.repoName = (TextView)view.findViewById(R.id.repoName);
            holder.repoDesc = (TextView)view.findViewById(R.id.repoDesc);
            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder)view.getTag();
        holder.repoName.setText(getItem(i).getName());
        if(getItem(i).getDescription() != null)
            holder.repoDesc.setText(getItem(i).getDescription());
        else
            holder.repoDesc.setText(context.getString(R.string.no_desc_found));

        return view;
    }
}
