package com.hpkarugendo.services;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HomebaseStorageService {

    private Environment environment;
    private PhotoRepository pRepo;
    private GalleryRepository gRepo;
    private CloudStorageAccount cloudStorageAccount;
    private CloudBlobClient cloudBlobClient;
    private CloudBlobContainer cloudBlobContainer;

    @Autowired
    public HomebaseStorageService(PhotoRepository pRepo, GalleryRepository gRepo, Environment environment) throws Exception {
        this.pRepo = pRepo;
        this.gRepo = gRepo;
        this.environment = environment;
        this.cloudStorageAccount = CloudStorageAccount.parse(environment.getProperty("azure.storage.connectionString"));
        this.cloudBlobClient = cloudStorageAccount.createCloudBlobClient();
    }

    public HomebaseStorageService() {
    }

    /*
    *
    *
    *
    --- The following methods deal with Azure Storage Services ---
    *
    *
    *
    */
    public boolean createContainer(String containerName){
        boolean containerCreated = false;

        this.cloudBlobContainer = null;
        try {
            cloudBlobContainer = cloudBlobClient.getContainerReference(containerName);
        } catch (URISyntaxException | StorageException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            containerCreated = cloudBlobContainer.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return containerCreated;
    }

    public URI uploadImageToContainer(String container, MultipartFile image) throws URISyntaxException, StorageException {
        URI uri = null;
        CloudBlockBlob blob = null;

        if(container != null || !container.isEmpty() && !image.isEmpty()){
            try {
                cloudBlobContainer = cloudBlobClient.getContainerReference(container);
                if(!cloudBlobContainer.exists()){
                    createContainer(container);
                    cloudBlobContainer = cloudBlobClient.getContainerReference(container);
                }
                blob = cloudBlobContainer.getBlockBlobReference(image.getOriginalFilename().replace("_", ""));
                blob.upload(image.getInputStream(), -1);
                uri = blob.getUri();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (StorageException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return uri;
    }

    public String getSavedImageUrl(String container, MultipartFile file) throws URISyntaxException, StorageException {
        String origin = uploadImageToContainer(container, file).toString();

        String replacer = "https://homebase-3113.azureedge.net";
        String toReplace = origin.substring(44, origin.length());
        String ans = replacer + toReplace;

        return ans;
    }

    public List<URI> uploadImagesToContainer(String container, MultipartFile[] images){
        List<URI> uris = new ArrayList<>();

        for(int a = 0; a < images.length; a++){
            if(images[a].getSize() > 10){
                URI uri = null;
                CloudBlockBlob blob = null;
                try {
                    blob = cloudBlobContainer.getBlockBlobReference(images[a].getOriginalFilename());
                    blob.upload(images[a].getInputStream(), -1);
                    uri = blob.getUri();
                    uris.add(uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (StorageException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uris;
    }

    public boolean deleteImageFromContainer(String containerName, String imageName){
        boolean ans = false;

        try {
            cloudBlobContainer = cloudBlobClient.getContainerReference(containerName);
            CloudBlockBlob blobToBeDeleted = cloudBlobContainer.getBlockBlobReference(imageName);
            blobToBeDeleted.deleteIfExists();
            ans = true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return ans;
    }

    public boolean deleteContainer(String container){
        boolean ans = false;
        String toDelete = container;

        try {
            cloudBlobContainer = cloudBlobClient.getContainerReference(toDelete);
            if(cloudBlobContainer != null)
                cloudBlobContainer.deleteIfExists();
            ans = true;
        } catch (StorageException | URISyntaxException ex) {
            ans = false;
        }

        return ans;
    }

    /*
    *
    *
    *
    --- The following methods deal with Azure Database Services ---
    *
    *
    *
    */
    public boolean saveGallery(Gallery gallery){
        boolean ans = false;
        Gallery toSave = gallery;
        Gallery saved = gRepo.save(toSave);

        if(saved != null && saved.getName() == toSave.getName()){
            createContainer(saved.getName());
            ans = true;
        }

        return ans;
    }

    public Gallery getGalleryById(String id){
        Gallery toReturn = null;

        Optional<Gallery> go = gRepo.findById(id);
        toReturn = go.get();

        return toReturn;
    }

    public Gallery getGalleryByName(String name){
        Gallery toReturn = null;

        Optional<Gallery> go = gRepo.findByName(name);

        if(go.isPresent()){
            toReturn = go.get();
        }

        return toReturn;
    }

    public List<Gallery> listGalleries(){
        List<Gallery> toReturn = gRepo.findAllByOrderById();

        return toReturn;
    }

    public List<Gallery> listTop5Galleries(){
        List<Gallery> toReturn = gRepo.findTop5ByOrderByIdDesc();

        return toReturn;
    }

    public boolean deleteGallery(String id){
        boolean ans = false;

        Optional<Gallery> go = gRepo.findById(id);
        Gallery toDelete = go.get();

        try {
            gRepo.delete(toDelete);
            ans = true;
        } catch (Exception e) {
            ans = false;
        }

        return ans;
    }

    public Photo savePhoto(Photo photo){
        Photo toSave = photo;
        Photo saved = pRepo.save(toSave);

        if(saved != null && saved.getName() == toSave.getName()){
            return saved;
        }

        return null;
    }

    public Photo getPhoto(String id){
        Photo toReturn = null;
        Optional<Photo> po = pRepo.findById(id);
        toReturn = po.get();

        return toReturn;
    }

    public List<Photo> listPhotos(){
        List<Photo> toReturn = pRepo.findAllByOrderById();

        return toReturn;
    }

    public List<Photo> listTop5Photos(){
        List<Photo> toReturn = pRepo.findTop5ByOrderByIdDesc();

        return toReturn;
    }

    public boolean deletePhoto(String id){
        boolean ans = false;
        Optional<Photo> po = pRepo.findById(id);
        Photo toDelete = po.get();

        try {
            deleteImageFromContainer(toDelete.getGallery().getName(), toDelete.getName());
            pRepo.delete(toDelete);
            ans = true;
        } catch (Exception e) {
            ans = false;
        }

        return ans;
    }

}
