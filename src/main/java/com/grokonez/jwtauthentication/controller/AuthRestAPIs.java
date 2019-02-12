package com.grokonez.jwtauthentication.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.grokonez.jwtauthentication.message.request.GoupeForm;
import com.grokonez.jwtauthentication.message.request.LoginForm;
import com.grokonez.jwtauthentication.message.request.PhaseForm;
import com.grokonez.jwtauthentication.message.request.ProjetForm;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.request.TacheForm;
import com.grokonez.jwtauthentication.message.response.GroupeUsers;
import com.grokonez.jwtauthentication.message.response.InfosUser;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.message.response.ResponseMessage;
import com.grokonez.jwtauthentication.message.response.UserGroupes;
import com.grokonez.jwtauthentication.model.DocumentProjet;
import com.grokonez.jwtauthentication.model.Groupe;
import com.grokonez.jwtauthentication.model.GroupeUser;
import com.grokonez.jwtauthentication.model.Phase;
import com.grokonez.jwtauthentication.model.ProductionTache;
import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.ProjetGroupe;
import com.grokonez.jwtauthentication.model.Role;
import com.grokonez.jwtauthentication.model.RoleName;
import com.grokonez.jwtauthentication.model.Tache;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.DocumentProjetRepository;
import com.grokonez.jwtauthentication.repository.GroupeRepository;
import com.grokonez.jwtauthentication.repository.GroupeUserRepository;
import com.grokonez.jwtauthentication.repository.PhaseRepository;
import com.grokonez.jwtauthentication.repository.ProductionTacheRepository;
import com.grokonez.jwtauthentication.repository.ProjetGroupeRepository;
import com.grokonez.jwtauthentication.repository.ProjetRepository;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.TacheRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import com.grokonez.jwtauthentication.security.jwt.JwtProvider;
import com.grokonez.jwtauthentication.security.services.UserPrinciple;
import com.grokonez.jwtauthentication.services.SendMail;
import com.grokonez.jwtauthentication.services.StorageService;


@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ProductionTacheRepository productionTacheRepository;
	
	@Autowired
	DocumentProjetRepository documentProjetRepository;
	 
	@Autowired
	TacheRepository tacheRepository;
	
	@Autowired
	GroupeUserRepository groupeUserRepository;
	
	@Autowired
	GroupeRepository groupeRepository;
	
	@Autowired
	ProjetRepository projetRepository;
	
	@Autowired
	PhaseRepository phaseRepository;
	
	@Autowired
	ProjetGroupeRepository projetGroupeRepository;
	
	
	 
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	StorageService storageService;
	
	List<String> files = new ArrayList<String>();

	
	@PostMapping("/profile")
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		//String message = "";
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
	        Optional<User> user=userRepository.findById(userSup.getId());
	        User current=user.get();
            storageService.store(file,current.getId());
            current.setProfil(current.getId().toString()+".png");
            userRepository.save(current);
			
		} catch (Exception e) {
			//message = "FAIL to upload " + file.getOriginalFilename() + "!";
			//return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	@PostMapping("/production_tache")
	public ProductionTache production_tache(@RequestParam("file") MultipartFile file,
								 @RequestParam("eleve_id") Long eleve_id,
								 @RequestParam("tache_id") Long tache_id) {
		//String message = "";
		try {
			ProductionTache productionTache=new ProductionTache();
			productionTache.setTache1(tacheRepository.findById(tache_id).get());
			productionTache.setEleve(userRepository.findById(eleve_id).get());
			productionTacheRepository.save(productionTache);
			String doc=storageService.production(file,productionTache.getId());
			productionTache.setDate(new Date());
			productionTache.setDocument(doc);
			productionTacheRepository.save(productionTache);
			return productionTache;
			
		} catch (Exception e) {
			//message = "FAIL to upload " + file.getOriginalFilename() + "!";
			//return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			return new ProductionTache();
		}
	}
	
	@PostMapping("/document_projet")
	public DocumentProjet document_projet(@RequestParam("file") MultipartFile file,
								 @RequestParam("projet_id") Long projet_id) {
		//String message = "";
		try {
			DocumentProjet document=new DocumentProjet();
			document.setProjet3(projetRepository.findById(projet_id).get()); 
			documentProjetRepository.save(document);
			String doc=storageService.document(file,document.getId());
			document.setDocument(doc);
			documentProjetRepository.save(document);
			return document;
			
		} catch (Exception e) {
			//message = "FAIL to upload " + file.getOriginalFilename() + "!";
			//return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			return new DocumentProjet();
		}
	}
	
	@PostMapping("/delete_document_projet")
	public DocumentProjet delete_document_projet(@RequestParam("id") Long id) {
		DocumentProjet document=documentProjetRepository.findById(id).get();
		documentProjetRepository.delete(document);
		return document;
	}
	
	@PostMapping("/delete_production_tache")
	public ProductionTache delete_production_tache(@RequestParam("id") Long id) {
		ProductionTache productionTache=productionTacheRepository.findById(id).get();
		productionTacheRepository.delete(productionTache);
		return productionTache;
	}
	@GetMapping("/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(AuthRestAPIs.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
 
		return ResponseEntity.ok().body(fileNames);
	}
	
	@GetMapping("/test")
	public String test() {
		 return "test fskdf dfuksdb fsd dfsdkf sdfk ";
	}
	
	@GetMapping("/verify_passe/{id}/{password}")
	public Boolean verify_passe(@PathVariable Long id,@PathVariable String password) {
		User user=userRepository.getOne(id);
		System.out.println(encoder.encode(password));
		System.out.println(user.getPassword());
		return encoder.encode(password).equals(user.getPassword());
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/docs/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> docs(@PathVariable String filename) {
		Resource file = storageService.loaddocs(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/production/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> production(@PathVariable String filename) {
		Resource file = storageService.loadproduction(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		 
		UserPrinciple user1 = (UserPrinciple)authentication.getPrincipal();
		Optional<User> sup=userRepository.findById(user1.getId());
			// Creating user's account
	    User user=sup.get();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),user, userDetails.getAuthorities()));
	}
	
	/*@PostMapping("/sendmail")
	public void sendmail(@RequestParam("email") String email,
						 @RequestParam("subject") String subject,
						 @RequestParam("msg") String msg,
						 @RequestParam("nbr") Integer nbr,
						 @RequestParam("files") List<BodyPart> files) {
 
		SendMail sendMail=new SendMail();
	 
		if(nbr==0){
			sendMail.envoi(subject, email, msg);
		}else{
			sendMail.envoi(subject, email, msg,files);
		}
		
	}*/
	
	@PostMapping("/sendmail")
	public void sendmail(@RequestParam("email") String email,
						 @RequestParam("subject") String subject,
						 @RequestParam("msg") String msg){
 
		SendMail sendMail=new SendMail();
	    sendMail.envoi(subject, email, msg);
		 
		
	}
	
	@PostMapping("/add_project")
	public void add_project(@Valid @RequestBody ProjetForm projet){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        
        Projet p=new Projet();
        p.setDateDebut(projet.getDate_debut());
        p.setDateFin(projet.getDate_fin());
        p.setNom(projet.getNom());
        p.setDescription(projet.getDescription());
        p.setDateCreation(new Date());
        p.setDateModification(new Date());
        p.setProfesseur(current);
        projetRepository.save(p);
        
        List<PhaseForm> phases=projet.getPhases();
        for (Iterator iterator = phases.iterator(); iterator.hasNext();) {
			PhaseForm phase = (PhaseForm) iterator.next();
			Phase phase1=new Phase();
 			phase1.setNom(phase.getNom()); 
	        phase1.setDateDebut(phase.getDate_debut());
	        phase1.setDateFin(phase.getDate_fin());
	        phase1.setDescription(phase.getDescription());
	        phase1.setProjet2(p);
	        phaseRepository.save(phase1);
	        List<TacheForm> taches=phase.getTaches();
	        for (Iterator iterator2 = taches.iterator(); iterator2.hasNext();) {
				TacheForm tache = (TacheForm) iterator2.next();
				Tache t=new Tache();
				t.setNom(tache.getNom());
		        t.setDescription(tache.getDescription());
		        t.setDateDebut(tache.getDate_debut());
		        t.setDateFin(tache.getDate_fin());
		        t.setPhase(phase1);
		        tacheRepository.save(t);
			}
		}
        
        for (Iterator iterator = projet.getGroupes().iterator(); iterator.hasNext();) {
			Long g = (Long) iterator.next();
			ProjetGroupe pg=new ProjetGroupe();
			Groupe groupe=groupeRepository.getOne(g);
			pg.setGroupe(groupe);
			pg.setProjet(p);
			projetGroupeRepository.save(pg);
		}
         
	}
	
	@PostMapping("/add_groupe")
	public void add_groupe(@Valid @RequestBody GoupeForm groupe){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        
        Groupe g=new Groupe(); 
        g.setNom(groupe.getNom()); 
        g.setProfesseur(current);
        groupeRepository.save(g);
        
        List<Long> users=groupe.getUsers();
        for (Iterator iterator = users.iterator(); iterator.hasNext();) {
        	Long u = (Long) iterator.next();
        	User user2=userRepository.getOne(u);
			GroupeUser gu=new GroupeUser();
			gu.setGroupe1(g);
			gu.setUser1(user2);
			if(u==groupe.getCoordinateur()){
				gu.setCoordinateur(true);
			}else{
				gu.setCoordinateur(false);
			}
			
	        groupeUserRepository.save(gu); 
		}
          
	}
	
	
	
	@GetMapping("/init")
	public ResponseEntity<?> init() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        User superieur=new User();
        Collection<Projet> projets=new ArrayList<>();
        
        if(current.getUserSuperieur()==null){
        	System.out.println("prof");
        	superieur=current.getUserSuperieur();
        	projets=current.getProjets();
        }else{
        	System.out.println("eleve");
        	for (Iterator iterator = current.getGroupeUsers().iterator(); iterator.hasNext();) {
        		GroupeUser p = (GroupeUser) iterator.next();
        		System.out.println(p.getId());
        		System.out.println("size "+p.getGroupe1().getProjetGroupes().size());
        		for (Iterator i = p.getGroupe1().getProjetGroupes().iterator(); i.hasNext();) {
        			ProjetGroupe pg = (ProjetGroupe) i.next();
        			System.out.println(pg.getId()+" "+pg.getGroupe().getId());
	        		projets.add(pg.getProjet());
        		}
    		}
        }
        
		return ResponseEntity.ok(new InfosUser(superieur,projets,current.getEleves(),current.getProductionTaches(),current.getGroupeUsers()));
	}
	
	
	@GetMapping("/groupes")
	public Collection<Groupe> groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
		return groupeRepository.findByProfesseur(current);
	}
	
	@GetMapping("/mes_groupes")
	public Collection<UserGroupes> mes_groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        Collection<UserGroupes> groupes=new HashSet<>();
        for (Iterator i = current.getGroupeUsers().iterator(); i.hasNext();) {
			GroupeUser pu = (GroupeUser)i.next();
			UserGroupes ug=new UserGroupes(pu.getGroupe1());
			ug.setUsers(pu.getGroupe1().getGroupeUsers());
		    groupes.add(ug);
		}
        
		return groupes;
	}
	
	@GetMapping("/gestion_groupes")
	public Collection<GroupeUsers> gestion_groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        List<GroupeUsers> groupes=new ArrayList<>();
        
        for (Iterator iterator = groupeRepository.findByProfesseur(current).iterator(); iterator.hasNext();) {
			Groupe groupe = (Groupe) iterator.next();
			GroupeUsers groupeUsers=new GroupeUsers(groupe);
			groupeUsers.setUsers(groupe.getGroupeUsers());
			groupes.add(groupeUsers);
		}
         
		return groupes;
	}
	
	@GetMapping("/liste_eleves")
	public List<User> liste_eleves() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
        Optional<User> user=userRepository.findById(userSup.getId());
        User current=user.get();
        List<User> eleves=userRepository.findByUserSuperieur(current);

		return eleves;
	}
	
	@GetMapping("/all_users")
	public List<User> all_users() {
        List<User> users=userRepository.findAll();
        return users;
	}
	
	
	
	@PostMapping("/modifier_info")
	public ResponseEntity<?> modifier_info( @RequestParam("email") String email,
											@RequestParam("lastName") String lastName,
											@RequestParam("name") String name,
											@RequestParam("username") String username,
											@RequestParam("id") Long id) {
		
		List<User> user=userRepository.findAll();
		
		for (Iterator iterator = user.iterator(); iterator.hasNext();) {
			User user2 = (User) iterator.next();
			if(user2.getId()!=id && user2.getEmail().equals(email)){
				return new ResponseEntity<>(new ResponseMessage("Erreur : Email déjà utilisé !"),
						HttpStatus.BAD_REQUEST);	
			}
			if(user2.getId()!=id && user2.getUsername().equals(username)){
				return new ResponseEntity<>(new ResponseMessage("Erreur : Username déjà utilisé !"),
						HttpStatus.BAD_REQUEST);	
			}
		}
		
		 
		User user1=userRepository.getOne(id);
		user1.setEmail(email);
		user1.setLastName(lastName);
		user1.setName(name);
		user1.setUsername(username);
		userRepository.save(user1);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Erreur : username déjà utilisé !"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Erreur : email déjà utilisé !"),
					HttpStatus.BAD_REQUEST);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple)auth.getPrincipal();
		//System.out.println(user.getId()+"--"+user.getEmail());
        Optional<User> sup=userRepository.findById(userSup.getId());
		// Creating user's account
        User superieur=sup.get();
		User user = new User(signUpRequest.getName(),signUpRequest.getLastName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		user.setProfil("no-image.png");
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "eleve":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_ELEVE)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_PROFESSEUR)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		user.setUserSuperieur(superieur);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}