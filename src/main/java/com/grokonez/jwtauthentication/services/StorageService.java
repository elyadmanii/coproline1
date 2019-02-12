package com.grokonez.jwtauthentication.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
 
@Service
public class StorageService {
 
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");
	private final Path rootdocs = Paths.get("attachements");
	private final Path production = Paths.get("production");
	
 
	public void store(MultipartFile file,Long id) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(id.toString()+".png"),StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
	public String production(MultipartFile file,Long id) {
		try {
			String name=id.toString()+"."+getExtensionByStringHandling(file.getOriginalFilename()).get();
			Files.copy(file.getInputStream(), this.production.resolve(name),StandardCopyOption.REPLACE_EXISTING);
			return name;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
	public String document(MultipartFile file,Long id) {
		try {
			String name=id.toString()+"."+getExtensionByStringHandling(file.getOriginalFilename()).get();
			Files.copy(file.getInputStream(), this.rootdocs.resolve(name),StandardCopyOption.REPLACE_EXISTING);
			return name;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
	public Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	
	
 
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
	public Resource loaddocs(String filename) {
		try {
			Path file = rootdocs.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
	public Resource loadproduction(String filename) {
		try {
			Path file = production.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
 
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
 
	public void init() {
		try {
			Files.createDirectory(rootLocation);
			Files.createDirectory(rootdocs);
			Files.createDirectory(production);
			  
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
