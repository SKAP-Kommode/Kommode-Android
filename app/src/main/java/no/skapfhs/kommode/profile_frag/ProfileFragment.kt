package no.skapfhs.kommode.profile_frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import no.skapfhs.kommode.KommodeRepo
import no.skapfhs.kommode.databinding.FragmentProfileBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val repo = KommodeRepo()

        binding.prefBogruppe.setOnClickListener {
            Toast.makeText(context, "Kun ledelsen kan endre bogruppe.", Toast.LENGTH_SHORT).show()
        }

        binding.prefVaskerom.setOnClickListener {
            // TODO: Hent vaskerom fra db, og vis hvilke man kan velge mellom
            val launderRooms = repo.getLaunderRooms()
            // Gi task videre til popup?
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}