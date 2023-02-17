package no.skapfhs.kommode.home_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import no.skapfhs.kommode.R

/*
* Hva skal vises på hjemmeskjermen?
* - Kort hilsen?
* - Nye kort siden sist (trykke på dem tar deg til feeden)
* - Timere etc til vaskemaskin reservasjon
* - Kort om hva som skjer på timeplanen akkurat nå (og kanskje senere?)
* - Viktige beskjeder fra infoskjermen
*
* */

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}