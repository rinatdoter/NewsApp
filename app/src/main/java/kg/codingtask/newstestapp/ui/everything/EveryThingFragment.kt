package kg.codingtask.newstestapp.ui.everything

import kg.codingtask.newstestapp.ui.base.ArticlesBaseFragment

class EveryThingFragment : ArticlesBaseFragment() {
    override fun refresh() {
        vm.refreshEveryThing()
    }

    override fun load() {
        vm.loadAllArticles()
    }
}