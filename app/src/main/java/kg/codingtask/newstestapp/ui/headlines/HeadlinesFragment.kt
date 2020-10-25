package kg.codingtask.newstestapp.ui.headlines

import kg.codingtask.newstestapp.ui.base.ArticlesBaseFragment

class HeadlinesFragment : ArticlesBaseFragment() {
    override fun refresh() {
        vm.refreshHeadlines()
    }

    override fun load() {
        vm.loadArticles()
    }
}