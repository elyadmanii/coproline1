package com.grokonez.jwtauthentication.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.grokonez.jwtauthentication.message.request.GoupeForm;
import com.grokonez.jwtauthentication.message.request.LoginForm;
import com.grokonez.jwtauthentication.message.request.PhaseForm;
import com.grokonez.jwtauthentication.message.request.PhaseForm1;
import com.grokonez.jwtauthentication.message.request.ProjetForm;
import com.grokonez.jwtauthentication.message.request.ProjetGroupeForm;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.request.SousTacheForm;
import com.grokonez.jwtauthentication.message.request.TacheEleveForm;
import com.grokonez.jwtauthentication.message.request.TacheForm;
import com.grokonez.jwtauthentication.message.request.TacheForm1;
import com.grokonez.jwtauthentication.message.response.GroupeUsers;
import com.grokonez.jwtauthentication.message.response.InfosPhase;
import com.grokonez.jwtauthentication.message.response.InfosTache;
import com.grokonez.jwtauthentication.message.response.InfosUser;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.message.response.ResponseMessage;
import com.grokonez.jwtauthentication.message.response.UserGroupes;
import com.grokonez.jwtauthentication.model.DBFile;
import com.grokonez.jwtauthentication.model.DocumentProjet;
import com.grokonez.jwtauthentication.model.Groupe;
import com.grokonez.jwtauthentication.model.GroupeUser;
import com.grokonez.jwtauthentication.model.Phase;
import com.grokonez.jwtauthentication.model.ProductionTache;
import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.ProjetGroupe;
import com.grokonez.jwtauthentication.model.Role;
import com.grokonez.jwtauthentication.model.RoleName;
import com.grokonez.jwtauthentication.model.SousTache;
import com.grokonez.jwtauthentication.model.Tache;
import com.grokonez.jwtauthentication.model.TacheEleve;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.DocumentProjetRepository;
import com.grokonez.jwtauthentication.repository.GroupeRepository;
import com.grokonez.jwtauthentication.repository.GroupeUserRepository;
import com.grokonez.jwtauthentication.repository.PhaseRepository;
import com.grokonez.jwtauthentication.repository.ProductionTacheRepository;
import com.grokonez.jwtauthentication.repository.ProjetGroupeRepository;
import com.grokonez.jwtauthentication.repository.ProjetRepository;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.SousTacheRepository;
import com.grokonez.jwtauthentication.repository.TacheEleveRepository;
import com.grokonez.jwtauthentication.repository.TacheRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import com.grokonez.jwtauthentication.security.jwt.JwtProvider;
import com.grokonez.jwtauthentication.security.services.DBFileStorageService;
import com.grokonez.jwtauthentication.security.services.UserPrinciple;
import com.grokonez.jwtauthentication.services.SendMail;
import com.grokonez.jwtauthentication.services.StorageService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;
	// test comment

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
	SousTacheRepository sousTacheRepository;

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
	TacheEleveRepository tacheEleveRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	StorageService storageService;

	@Autowired
	private DBFileStorageService DBFileStorageService;

	List<String> files = new ArrayList<String>();

	@PostMapping("/profile")
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {

		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
			Optional<User> user = userRepository.findById(userSup.getId());
			User current = user.get();
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			try {
				if (fileName.contains("..")) {
					throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
				}
				current.setData(file.getBytes());
				current.setFileName(fileName);
				current.setFileType(file.getContentType());
				userRepository.save(current);

			} catch (IOException ex) {
				throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		DBFile dbFile;
		try {
			dbFile = DBFileStorageService.getFile(fileId);
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
					.body(new ByteArrayResource(dbFile.getData()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping("/production_tache")
	public ProductionTache production_tache(@RequestParam("file") MultipartFile file,
			@RequestParam("eleve_id") Long eleve_id, @RequestParam("tache_id") Long tache_id) {
		// String message = "";
		try {
			ProductionTache productionTache = new ProductionTache();
			productionTache.setTache1(tacheRepository.findById(tache_id).get());
			productionTache.setEleve(userRepository.findById(eleve_id).get());
			productionTache.setUuid(UUID.randomUUID().toString());
			productionTacheRepository.save(productionTache);
			// String doc=storageService.production(file,productionTache.getId());
			productionTache.setDate(new Date());
			// productionTache.setDocument(doc);

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			try {
				if (fileName.contains("..")) {
					throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
				}
				productionTache.setData(file.getBytes());
				productionTache.setFileName(fileName);
				productionTache.setFileType(file.getContentType());

			} catch (IOException ex) {
				throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
			}

			productionTacheRepository.save(productionTache);
			return productionTache;

		} catch (Exception e) {
			// message = "FAIL to upload " + file.getOriginalFilename() + "!";
			// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			return new ProductionTache();
		}

	}

	@PostMapping("/document_projet")
	public DocumentProjet document_projet(@RequestParam("file") MultipartFile file,
			@RequestParam("projet_id") Long projet_id) {
		// String message = "";
		try {
			DocumentProjet document = new DocumentProjet();
			document.setProjet3(projetRepository.findById(projet_id).get());
			document.setUuid(UUID.randomUUID().toString());
			documentProjetRepository.save(document);
			// String doc=storageService.document(file,document.getId());
			// document.setDocument(doc);
			documentProjetRepository.save(document);

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			try {
				if (fileName.contains("..")) {
					throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
				}
				document.setData(file.getBytes());
				document.setFileName(fileName);
				document.setDocument(fileName);
				document.setFileType(file.getContentType());
				document.setUuid(UUID.randomUUID().toString());
				documentProjetRepository.save(document);

			} catch (IOException ex) {
				throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
			}

			return document;

		} catch (Exception e) {
			// message = "FAIL to upload " + file.getOriginalFilename() + "!";
			// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			return new DocumentProjet();
		}
	}

	@PostMapping("/delete_document_projet")
	public DocumentProjet delete_document_projet(@RequestParam("id") Long id) {
		DocumentProjet document = documentProjetRepository.findById(id).get();
		documentProjetRepository.delete(document);
		return document;
	}

	@PostMapping("/delete_production_tache")
	public ProductionTache delete_production_tache(@RequestParam("id") Long id) {
		ProductionTache productionTache = productionTacheRepository.findById(id).get();
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
	public Boolean verify_passe(@PathVariable Long id, @PathVariable String password) {
		User user = userRepository.getOne(id);
		System.out.println(encoder.encode(password));
		System.out.println(user.getPassword());
		return encoder.encode(password).equals(user.getPassword());
	}

	/*
	 * @GetMapping("/files/{filename:.+}")
	 * 
	 * @ResponseBody public ResponseEntity<Resource> getFile(@PathVariable String
	 * filename) { Resource file = storageService.loadFile(filename); return
	 * ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=\"" + file.getFilename() + "\"") .body(file); }
	 */

	@GetMapping("/files/{id}")
	public ResponseEntity<Resource> getFile(@PathVariable Long id) {
		User user = userRepository.getOne(id);

		if (user.getFileType() == null) {
			user = userRepository.getOne(10000L);
		}

		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(user.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getFileName() + "\"")
					.body(new ByteArrayResource(user.getData()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/docs/{id}")
	@ResponseBody
	public ResponseEntity<Resource> docs(@PathVariable Long id) {
		DocumentProjet document = documentProjetRepository.getOne(id);
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
					.body(new ByteArrayResource(document.getData()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@GetMapping("/production/{id}")
	@ResponseBody
	public ResponseEntity<Resource> production(@PathVariable Long id) {
		ProductionTache productionTache = productionTacheRepository.getOne(id);
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(productionTache.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + productionTache.getFileName() + "\"")
					.body(new ByteArrayResource(productionTache.getData()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		UserPrinciple user1 = (UserPrinciple) authentication.getPrincipal();
		Optional<User> sup = userRepository.findById(user1.getId());
		// Creating user's account
		User user = sup.get();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user, userDetails.getAuthorities()));
	}

	/*
	 * @PostMapping("/sendmail") public void sendmail(@RequestParam("email") String
	 * email,
	 * 
	 * @RequestParam("subject") String subject,
	 * 
	 * @RequestParam("msg") String msg,
	 * 
	 * @RequestParam("nbr") Integer nbr,
	 * 
	 * @RequestParam("files") List<BodyPart> files) {
	 * 
	 * SendMail sendMail=new SendMail();
	 * 
	 * if(nbr==0){ sendMail.envoi(subject, email, msg); }else{
	 * sendMail.envoi(subject, email, msg,files); }
	 * 
	 * }
	 */

	@PostMapping("/sendmail")
	public void sendmail(@RequestParam("email") String email, @RequestParam("subject") String subject,
			@RequestParam("msg") String msg) {

		SendMail sendMail = new SendMail();
		sendMail.envoi(subject, email, msg);

	}

	@PostMapping("/add_phase")
	public ResponseEntity<?> add_phase(@Valid @RequestBody PhaseForm1 phase) {
		Projet p = projetRepository.findById(phase.getProjet()).get();

		Phase phase1 = new Phase();
		phase1.setNom(phase.getNom());
		phase1.setDateDebut(phase.getDd());
		phase1.setDateFin(phase.getDf());
		phase1.setDescription(phase.getDescription());
		phase1.setProjet2(p);
		phase1.setUuid(UUID.randomUUID().toString());
		phaseRepository.save(phase1);

		return ResponseEntity.ok(new InfosPhase(phase1));

	}

	@PostMapping("/update_phase")
	public ResponseEntity<?> update_phase(@Valid @RequestBody PhaseForm1 phase) {

		Phase phase1 = phaseRepository.findById(phase.getProjet()).get();
		phase1.setNom(phase.getNom());
		phase1.setDateDebut(phase.getDd());
		phase1.setDateFin(phase.getDf());
		phase1.setDescription(phase.getDescription());
		phaseRepository.save(phase1);

		return ResponseEntity.ok(new InfosPhase(phase1));

	}

	@PostMapping("/delete_phase")
	public void delete_phase(@RequestParam("id") Long id) {
		Phase phase = phaseRepository.findById(id).get();
		phaseRepository.delete(phase);
	}

	@PostMapping("/delete_user")
	public void delete_user(@RequestParam("id") Long id) {
		User user = userRepository.findById(id).get();
		userRepository.delete(user);
	}

	@PostMapping("/update_projet")
	public void update_projet(@Valid @RequestBody PhaseForm1 phase) {

		Projet p = projetRepository.findById(phase.getProjet()).get();
		p.setNom(phase.getNom());
		p.setDateDebut(phase.getDd());
		p.setDateFin(phase.getDf());
		p.setDescription(phase.getDescription());
		projetRepository.save(p);

	}

	@PostMapping("/delete_projet")
	public void delete_projet(@RequestParam("id") Long id) {
		Projet p = projetRepository.findById(id).get();
		projetRepository.delete(p);
	}

	@PostMapping("/tache_eleves")
	public ResponseEntity<?> tache_eleves(@Valid @RequestBody TacheEleveForm tacheEleves) {

		List<TacheEleve> tacheEleve = tacheEleveRepository.findAllByTache_eleve(tacheEleves.getTache());
		System.out.println(tacheEleve.size());
		for (Iterator iterator = tacheEleve.iterator(); iterator.hasNext();) {
			TacheEleve tacheEleve2 = (TacheEleve) iterator.next();
			System.out.println(tacheEleve2.getEleve_tache().getUsername());
			tacheEleveRepository.delete(tacheEleve2);
		}
		Tache tache = tacheRepository.getOne(tacheEleves.getTache());

		System.out.println(tacheEleves.getEleves().size());
		for (Long eleve : tacheEleves.getEleves()) {
			System.out.println(eleve);
			TacheEleve t_eleve = new TacheEleve();
			User user = userRepository.getOne(eleve);
			t_eleve.setEleve_tache(user);
			t_eleve.setTache_eleve(tache);
			tacheEleveRepository.save(t_eleve);

		}

		return ResponseEntity.ok(new InfosTache(tache));
	}

	@GetMapping("/get_tache_eleves/{id}")
	public List<TacheEleve> get_tache_eleves(@PathVariable Long id) {

		List<TacheEleve> tacheEleve = tacheEleveRepository.findAllByTache_eleve(id);
		return tacheEleve;
	}

	@PostMapping("/projet_groupes")
	public void projet_groupes(@Valid @RequestBody ProjetGroupeForm pg) {

		Long projet = pg.getProjet();
		List<Long> groupes_deleted = pg.getGroupes_deleted();
		List<Long> groupes_added = pg.getGroupes_added();

		Projet p = projetRepository.findById(projet).get();

		for (int i = 0; i < groupes_deleted.size(); i++) {
			List<ProjetGroupe> projetGroupes = projetGroupeRepository.findAllByProjet(p);

			for (Iterator iterator = projetGroupes.iterator(); iterator.hasNext();) {
				ProjetGroupe projetGroupe = (ProjetGroupe) iterator.next();
				if (projetGroupe.getGroupe().getId() == groupes_deleted.get(i)) {
					projetGroupeRepository.delete(projetGroupe);
				}
			}
		}

		for (int i = 0; i < groupes_added.size(); i++) {
			ProjetGroupe projetGroupe = new ProjetGroupe();
			Groupe g = groupeRepository.findById(groupes_added.get(i)).get();
			projetGroupe.setGroupe(g);
			projetGroupe.setProjet(p);
			projetGroupeRepository.save(projetGroupe);
		}
	}

	@PostMapping("/add_tache")
	public ResponseEntity<?> add_tache(@Valid @RequestBody TacheForm1 tache) {

		Phase phase = phaseRepository.findById(tache.getPhase()).get();

		Tache t = new Tache();
		t.setNom(tache.getNom());
		t.setDateDebut(tache.getDd());
		t.setDateFin(tache.getDf());
		t.setDescription(tache.getDescription());
		t.setPhase(phase);
		t.setUuid(UUID.randomUUID().toString());
		tacheRepository.save(t);

		return ResponseEntity.ok(new InfosTache(t));

	}

	@PostMapping("/update_tache")
	public ResponseEntity<?> update_tache(@Valid @RequestBody TacheForm1 tache) {

		Tache t = tacheRepository.findById(tache.getPhase()).get();
		t.setNom(tache.getNom());
		t.setDateDebut(tache.getDd());
		t.setDateFin(tache.getDf());
		t.setDescription(tache.getDescription());
		tacheRepository.save(t);

		return ResponseEntity.ok(new InfosTache(t));

	}

	@PostMapping("/delete_tache")
	public void delete_tache(@RequestParam("id") Long id) {
		Tache t = tacheRepository.findById(id).get();
		tacheRepository.delete(t);
	}

	@PostMapping("/add_sous_tache")
	public ResponseEntity<?> add_sous_tache(@Valid @RequestBody SousTacheForm sous_tache) {

		Tache tache = tacheRepository.findById(sous_tache.getTache()).get();

		SousTache t = new SousTache();
		t.setNom(sous_tache.getNom());
		t.setUuid(UUID.randomUUID().toString());
		t.setDescription(sous_tache.getDescription());
		t.setTache(tache);
		sousTacheRepository.save(t);

		return ResponseEntity.ok(new InfosTache(tache));

	}

	@PostMapping("/update_sous_tache")
	public ResponseEntity<?> update_sous_tache(@Valid @RequestBody SousTacheForm sous_tache) {

		SousTache t = sousTacheRepository.findById(sous_tache.getTache()).get();
		t.setNom(sous_tache.getNom());
		t.setDescription(sous_tache.getDescription());
		sousTacheRepository.save(t);

		return ResponseEntity.ok(new InfosTache(t.getTache()));

	}

	@PostMapping("/delete_sous_tache")
	public void delete_sous_tache(@RequestParam("id") Long id) {
		SousTache t = sousTacheRepository.findById(id).get();
		sousTacheRepository.delete(t);
	}

	@PostMapping("/add_project")
	public void add_project(@Valid @RequestBody ProjetForm projet) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();

		Projet p = new Projet();
		p.setUuid(UUID.randomUUID().toString());
		p.setDateDebut(projet.getDate_debut());
		p.setDateFin(projet.getDate_fin());
		p.setNom(projet.getNom());
		p.setDescription(projet.getDescription());
		p.setDateCreation(new Date());
		p.setDateModification(new Date());
		p.setProfesseur(current);
		projetRepository.save(p);

		List<PhaseForm> phases = projet.getPhases();
		for (Iterator iterator = phases.iterator(); iterator.hasNext();) {
			PhaseForm phase = (PhaseForm) iterator.next();
			Phase phase1 = new Phase();
			phase1.setNom(phase.getNom());
			phase1.setDateDebut(phase.getDate_debut());
			phase1.setDateFin(phase.getDate_fin());
			phase1.setDescription(phase.getDescription());
			phase1.setProjet2(p);
			phase1.setUuid(UUID.randomUUID().toString());
			phaseRepository.save(phase1);
			List<TacheForm> taches = phase.getTaches();
			for (Iterator iterator2 = taches.iterator(); iterator2.hasNext();) {
				TacheForm tache = (TacheForm) iterator2.next();
				Tache t = new Tache();
				t.setNom(tache.getNom());
				t.setDescription(tache.getDescription());
				t.setDateDebut(tache.getDate_debut());
				t.setDateFin(tache.getDate_fin());
				t.setPhase(phase1);
				t.setUuid(UUID.randomUUID().toString());
				tacheRepository.save(t);
			}
		}

		for (Iterator iterator = projet.getGroupes().iterator(); iterator.hasNext();) {
			Long g = (Long) iterator.next();
			ProjetGroupe pg = new ProjetGroupe();
			Groupe groupe = groupeRepository.getOne(g);
			pg.setGroupe(groupe);
			pg.setProjet(p);
			projetGroupeRepository.save(pg);
		}

	}

	@PostMapping("/delete_groupe")
	public void delete_groupe(@RequestParam("id") Long id) {
		Groupe g = groupeRepository.findById(id).get();
		groupeRepository.delete(g);
	}

	@PostMapping("/update_groupe")
	public void update_groupe(@Valid @RequestBody GoupeForm groupe) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();

		Groupe g = groupeRepository.findById(groupe.getId()).get();
		g.setNom(groupe.getNom());
		groupeRepository.save(g);

		List<GroupeUser> groupes = groupeUserRepository.findAllByGroupe(groupe.getId());

		for (GroupeUser groupeUser : groupes) {
			groupeUserRepository.delete(groupeUser);
		}

		List<Long> users = groupe.getUsers();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			Long u = (Long) iterator.next();
			User user2 = userRepository.getOne(u);
			GroupeUser gu = new GroupeUser();
			gu.setGroupe1(g);
			gu.setUser1(user2);
			if (u.equals(groupe.getCoordinateur())) {
				gu.setCoordinateur(true);
			} else {
				gu.setCoordinateur(false);
			}

			groupeUserRepository.save(gu);
		}

	}

	@PostMapping("/add_groupe")
	public void add_groupe(@Valid @RequestBody GoupeForm groupe) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();

		Groupe g = new Groupe();
		g.setNom(groupe.getNom());
		g.setProfesseur(current);
		g.setUuid(UUID.randomUUID().toString());
		groupeRepository.save(g);

		List<Long> users = groupe.getUsers();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			Long u = (Long) iterator.next();
			User user2 = userRepository.getOne(u);
			GroupeUser gu = new GroupeUser();
			gu.setGroupe1(g);
			gu.setUser1(user2);
			if (u == groupe.getCoordinateur()) {
				gu.setCoordinateur(true);
			} else {
				gu.setCoordinateur(false);
			}

			groupeUserRepository.save(gu);
		}

	}

	@GetMapping("/init")
	public ResponseEntity<?> init() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();
		User superieur = new User();
		Collection<Projet> projets = new ArrayList<>();

		if (current.getUserSuperieur() == null) {
			System.out.println("prof");
			superieur = current.getUserSuperieur();
			projets = current.getProjets();
		} else {
			System.out.println("eleve");
			for (Iterator iterator = current.getGroupeUsers().iterator(); iterator.hasNext();) {
				GroupeUser p = (GroupeUser) iterator.next();
				System.out.println(p.getId());
				System.out.println("size " + p.getGroupe1().getProjetGroupes().size());
				for (Iterator i = p.getGroupe1().getProjetGroupes().iterator(); i.hasNext();) {
					ProjetGroupe pg = (ProjetGroupe) i.next();
					System.out.println(pg.getId() + " " + pg.getGroupe().getId());
					projets.add(pg.getProjet());
				}
			}
		}

		return ResponseEntity.ok(new InfosUser(superieur, projets, current.getEleves(), current.getProductionTaches(),
				current.getGroupeUsers()));
	}

	@GetMapping("/groupes")
	public Collection<Groupe> groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();
		return groupeRepository.findByProfesseur(current);
	}

	@GetMapping("/mes_groupes")
	public Collection<UserGroupes> mes_groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();
		Collection<UserGroupes> groupes = new HashSet<>();
		for (Iterator i = current.getGroupeUsers().iterator(); i.hasNext();) {
			GroupeUser pu = (GroupeUser) i.next();
			UserGroupes ug = new UserGroupes(pu.getGroupe1());
			ug.setUsers(pu.getGroupe1().getGroupeUsers());
			groupes.add(ug);
		}

		return groupes;
	}

	@GetMapping("/gestion_groupes")
	public Collection<GroupeUsers> gestion_groupes() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();
		List<GroupeUsers> groupes = new ArrayList<>();

		for (Iterator iterator = groupeRepository.findByProfesseur(current).iterator(); iterator.hasNext();) {
			Groupe groupe = (Groupe) iterator.next();
			GroupeUsers groupeUsers = new GroupeUsers(groupe);
			groupeUsers.setUsers(groupe.getGroupeUsers());
			groupes.add(groupeUsers);
		}

		return groupes;
	}

	@GetMapping("/liste_eleves")
	public List<User> liste_eleves() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		Optional<User> user = userRepository.findById(userSup.getId());
		User current = user.get();
		List<User> eleves = userRepository.findByUserSuperieur(current);

		return eleves;
	}

	@GetMapping("/liste_professeurs")
	public List<User> liste_professeurs() {
		return userRepository.getAllProfesseurs();
	}

	@GetMapping("/all_users")
	public List<User> all_users() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@PostMapping("/modifier_info")
	public ResponseEntity<?> modifier_info(@RequestParam("email") String email,
			@RequestParam("lastName") String lastName, @RequestParam("name") String name,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("id") Long id) {

		List<User> user = userRepository.findAll();

		for (Iterator iterator = user.iterator(); iterator.hasNext();) {
			User user2 = (User) iterator.next();
			if (!user2.getId().equals(id) && user2.getEmail().equals(email)) {
				return new ResponseEntity<>(new ResponseMessage("Erreur : Email déjà utilisé !"),
						HttpStatus.BAD_REQUEST);
			}
			if (!user2.getId().equals(id) && user2.getUsername().equals(username)) {
				return new ResponseEntity<>(new ResponseMessage("Erreur : Username déjà utilisé !"),
						HttpStatus.BAD_REQUEST);
			}
		}

		User user1 = userRepository.getOne(id);
		user1.setEmail(email);
		user1.setLastName(lastName);
		user1.setName(name);
		user1.setUsername(username);
		user1.setPassword(encoder.encode(password));
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
			return new ResponseEntity<>(new ResponseMessage("Erreur : email déjà utilisé !"), HttpStatus.BAD_REQUEST);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userSup = (UserPrinciple) auth.getPrincipal();
		// System.out.println(user.getId()+"--"+user.getEmail());
		Optional<User> sup = userRepository.findById(userSup.getId());
		// Creating user's account
		User superieur = sup.get();
		User user = new User(signUpRequest.getName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		user.setProfil("1000000.png");
		user.setUuid(UUID.randomUUID().toString());
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

	@PostMapping("/add_professeur")
	public ResponseEntity<?> add_professeur(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Erreur : username déjà utilisé !"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Erreur : email déjà utilisé !"), HttpStatus.BAD_REQUEST);
		}

		User user = new User(signUpRequest.getName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		user.setProfil("1000000.png");
		user.setUuid(UUID.randomUUID().toString());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName(RoleName.ROLE_PROFESSEUR)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@PostMapping("/update_user")
	public ResponseEntity<?> update_user(@Valid @RequestBody SignUpForm signUpRequest) {

		User user = userRepository.findById(signUpRequest.getId()).get();
		user.setLastName(signUpRequest.getLastName());
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@GetMapping("/exportProject/{id}")
	@ResponseBody
	public ResponseEntity<Resource> exportProject(@PathVariable Long id) {
		Projet projet = projetRepository.findById(id).get();
		generateXML(projet);
		// XML

		List<DocumentProjet> files = (List<DocumentProjet>) projet.getDocumentProjets();
		generateZIP(projet.getNom(), files);

		Resource file = storageService.loadFile("projet.zip");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	public void generateZIP(String zipName, List<DocumentProjet> filesToAdd) {

		byte[] buffer = new byte[1024];

		try {
			String workingDir = System.getProperty("user.dir");

			FileOutputStream fos = new FileOutputStream(workingDir + File.separator + "projet.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			ZipEntry ze1 = new ZipEntry(zipName + ".xml");
			zos.putNextEntry(ze1);
			FileInputStream in1 = new FileInputStream(workingDir + File.separator + "filexml.xml");
			int len1;
			while ((len1 = in1.read(buffer)) > 0) {
				zos.write(buffer, 0, len1);
			}
			in1.close();

			for (int i = 0; i < filesToAdd.size(); i++) {
				DocumentProjet document = filesToAdd.get(i);
				FileOutputStream f = new FileOutputStream(workingDir + File.separator + document.getDocument());
				f.write(document.getData());
				f.close();

				ZipEntry ze = new ZipEntry(document.getDocument());
				zos.putNextEntry(ze);
				FileInputStream in = new FileInputStream(workingDir + File.separator + document.getDocument());
				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				in.close();
			}
			zos.closeEntry();

			// remember close it
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void generateXML(Projet p) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element manifest = doc.createElement("manifest");
			doc.appendChild(manifest);

			Attr attr = doc.createAttribute("identifier");
			attr.setValue("manifest-" + UUID.randomUUID().toString());
			manifest.setAttributeNode(attr);

			Attr attr1 = doc.createAttribute("xsi:schemaLocation");
			attr1.setValue(
					"http://www.imsglobal.org/xsd/imscp_v1p1 http://www.imsglobal.org/xsd/imscp_v1p1.xsd http://www.imsglobal.org/xsd/imsld_v1p0 http://www.imsglobal.org/xsd/IMS_LD_Level_A.xsd");
			manifest.setAttributeNode(attr1);

			Attr attr2 = doc.createAttribute("xmlns:xsi");
			attr2.setValue("http://www.w3.org/2001/XMLSchema-instance");
			manifest.setAttributeNode(attr2);

			Attr attr3 = doc.createAttribute("xmlns:imsld");
			attr3.setValue("http://www.imsglobal.org/xsd/imsld_v1p0");
			manifest.setAttributeNode(attr3);

			Attr attr4 = doc.createAttribute("xmlns");
			attr4.setValue("http://www.imsglobal.org/xsd/imscp_v1p1");
			manifest.setAttributeNode(attr4);

			Element organizations = doc.createElement("organizations");
			manifest.appendChild(organizations);

			Element learning_design = doc.createElement("imsld:learning-design");

			Attr attr15 = doc.createAttribute("identifier");
			attr15.setValue("ld-" + p.getUuid());
			learning_design.setAttributeNode(attr15);

			Attr attr6 = doc.createAttribute("uri");
			attr6.setValue("http://www.coproline.org");
			learning_design.setAttributeNode(attr6);

			Attr attr7 = doc.createAttribute("sequence-used");
			attr7.setValue("false");
			learning_design.setAttributeNode(attr7);

			Attr attr8 = doc.createAttribute("level");
			attr8.setValue("A");
			learning_design.setAttributeNode(attr8);

			organizations.appendChild(learning_design);

			Element imsld_title = doc.createElement("imsld:title");
			imsld_title.appendChild(doc.createTextNode(p.getNom()));
			learning_design.appendChild(imsld_title);

			Element learning_objectives = doc.createElement("imsld:learning-objectives");
			learning_design.appendChild(learning_objectives);

			Element objectives_title = doc.createElement("imsld:title");
			objectives_title.appendChild(doc.createTextNode(p.getDescription()));
			learning_objectives.appendChild(objectives_title);

			Element components = doc.createElement("imsld:components");
			learning_design.appendChild(components);

			Element roles = doc.createElement("imsld:roles");
			components.appendChild(roles);

			List<ProjetGroupe> projetGroupes = (List<ProjetGroupe>) p.getProjetGroupes();

			for (ProjetGroupe projetGroupe : projetGroupes) {
				List<GroupeUser> groupeUsers = (List<GroupeUser>) projetGroupe.getGroupe().getGroupeUsers();

				Element learner = doc.createElement("imsld:learner");
				roles.appendChild(learner);

				Attr attr9 = doc.createAttribute("identifier");
				attr9.setValue("role-" + projetGroupe.getGroupe().getUuid());
				learner.setAttributeNode(attr9);

				Element learner_title = doc.createElement("imsld:title");
				learner_title.appendChild(doc.createTextNode(projetGroupe.getGroupe().getNom()));
				learner.appendChild(learner_title);

				for (GroupeUser groupeUser : groupeUsers) {

					Element student = doc.createElement("imsld:learner");
					learner.appendChild(student);

					Attr attr10 = doc.createAttribute("identifier");
					attr10.setValue("role-" + groupeUser.getUser1().getUuid());
					student.setAttributeNode(attr10);

					Element student_name = doc.createElement("imsld:title");
					student_name.appendChild(doc.createTextNode(
							groupeUser.getUser1().getName() + " " + groupeUser.getUser1().getLastName()));
					student.appendChild(student_name);
				}
			}

			Element staff = doc.createElement("imsld:staff");
			roles.appendChild(staff);

			Attr attr30 = doc.createAttribute("identifier");
			attr30.setValue("role-" + p.getProfesseur().getUuid());
			staff.setAttributeNode(attr30);

			Element staff_title = doc.createElement("imsld:title");
			staff_title.appendChild(
					doc.createTextNode(p.getProfesseur().getName() + " " + p.getProfesseur().getLastName()));
			staff.appendChild(staff_title);

			Element environments = doc.createElement("imsld:environments");
			components.appendChild(environments);

			Element environment = doc.createElement("imsld:environment");
			environments.appendChild(environment);

			String envUuid = UUID.randomUUID().toString();
			Attr attr11 = doc.createAttribute("identifier");
			attr11.setValue("env-" + envUuid);
			environment.setAttributeNode(attr11);

			Element environment_title = doc.createElement("imsld:title");
			environment_title.appendChild(doc.createTextNode("Coproline"));
			environment.appendChild(environment_title);

			List<Phase> phases = (List<Phase>) p.getPhases();
			for (ProjetGroupe projetGroupe : projetGroupes) {
				for (Phase phase : phases) {
					Element method = doc.createElement("imsld:method");
					learning_design.appendChild(method);

					Element method_play = doc.createElement("imsld:play");
					method.appendChild(method_play);

					Attr attr12 = doc.createAttribute("identifier");
					attr12.setValue("play-" + phase.getUuid());
					method_play.setAttributeNode(attr12);

					Attr attr13 = doc.createAttribute("isvisible");
					attr13.setValue("true");
					method_play.setAttributeNode(attr13);

					Element play_title = doc.createElement("imsld:title");
					play_title.appendChild(doc.createTextNode(phase.getNom()));
					method_play.appendChild(play_title);
					 

					// tache
					List<Tache> taches = (List<Tache>) phase.getTaches();
					for (Tache t : taches) {

						Element tache = doc.createElement("imsld:act");
						method_play.appendChild(tache);

						Attr attr14 = doc.createAttribute("identifier");
						attr14.setValue("act-" + t.getUuid());
						method_play.setAttributeNode(attr14);

						Element tache_title = doc.createElement("imsld:title");
						tache_title.appendChild(doc.createTextNode(t.getNom()));
						tache.appendChild(tache_title);
						 
						// Sous tache
						List<SousTache> sousTaches = (List<SousTache>) t.getSousTaches();
						for (SousTache s : sousTaches) {
							Element sousTache = doc.createElement("imsld:role-part");
							tache.appendChild(sousTache);
							s.setUuid(UUID.randomUUID().toString());
							Attr attr16 = doc.createAttribute("identifier");
							attr16.setValue("rolepart-" + s.getUuid());
							tache.setAttributeNode(attr16);

							Element sousTaches_title = doc.createElement("imsld:title");
							sousTaches_title.appendChild(doc.createTextNode(s.getNom()));
							sousTache.appendChild(sousTaches_title);

							Element role_ref = doc.createElement("imsld:role-ref");
							sousTache.appendChild(role_ref);

							Attr attr17 = doc.createAttribute("ref");
							String uid="";
							for (TacheEleve te : t.getTacheEleves()) {
								if(te.getTache_eleve().getId()==t.getId()) {
									uid=te.getEleve_tache().getUuid();
									break;
								}
							}
							
							attr17.setValue("role-"+uid);
							role_ref.setAttributeNode(attr17);

							Element env_ref = doc.createElement("imsld:environment-ref");
							sousTache.appendChild(env_ref);

							Attr attr18 = doc.createAttribute("ref");
							attr18.setValue("env-" + envUuid);
							env_ref.setAttributeNode(attr18);

						}
						
						Element tache_time = doc.createElement("imsld:complete-act");
						tache.appendChild(tache_time);
						
						long diffIn = Math.abs(t.getDateFin().getTime() - t.getDateDebut().getTime());
					    long task_days = TimeUnit.DAYS.convert(diffIn, TimeUnit.MILLISECONDS);
					    
						Element tache_time_days = doc.createElement("imsld:time-limit");
						tache_time_days.appendChild(doc.createTextNode("P"+task_days+"D"));
						tache_time.appendChild(tache_time_days);
					}
					
					Element play_time = doc.createElement("imsld:complete-play");
					method_play.appendChild(play_time);
					
					long diffInMillies = Math.abs(phase.getDateFin().getTime() - phase.getDateDebut().getTime());
				    long play_days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				    
					Element play_time_days = doc.createElement("imsld:time-limit");
					play_time_days.appendChild(doc.createTextNode("P"+play_days+"D"));
					play_time.appendChild(play_time_days);

				}
			}

			List<DocumentProjet> documents = (List<DocumentProjet>) p.getDocumentProjets();
			Element resources = doc.createElement("resources");
			manifest.appendChild(resources);

			for (DocumentProjet document : documents) {

				Element resource = doc.createElement("resource");
				resources.appendChild(resource);
				Attr attr17 = doc.createAttribute("identifier");
				attr17.setValue("resource-" + document.getUuid());
				resource.setAttributeNode(attr17);

				Attr attr18 = doc.createAttribute("href");
				attr18.setValue(document.getFileName());
				resource.setAttributeNode(attr18);

				Attr attr19 = doc.createAttribute("type");
				attr19.setValue(document.getFileType());
				resource.setAttributeNode(attr19);

				Element file = doc.createElement("file");
				resource.appendChild(file);

				Attr attr20 = doc.createAttribute("href");
				attr20.setValue(document.getFileName());
				file.setAttributeNode(attr20);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			String workingDir = System.getProperty("user.dir");
			StreamResult result = new StreamResult(new File(workingDir + File.separator + "filexml.xml"));

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

}