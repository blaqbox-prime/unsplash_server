package com.blaqboxdev.unsplash.Seed;

import com.blaqboxdev.unsplash.Models.Entities.Collection;
import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Role;
import com.blaqboxdev.unsplash.Repositories.CollectionRepository;
import com.blaqboxdev.unsplash.Repositories.ImageRepository;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import com.blaqboxdev.unsplash.Services.Implementations.ImageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PhotosSeedDataGenerator implements CommandLineRunner {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ImageServiceImp imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String[][] seedUserData = {
            {"john.doe@example.com", "John Doe", "johndoe", "https://i.pravatar.cc/150?img=12"},
            {"sarah.smith@example.com", "Sarah Smith", "sarahsmith", "https://i.pravatar.cc/150?img=45"},
            {"mike.johnson@example.com", "Mike Johnson", "mikej", "https://i.pravatar.cc/150?img=33"}
    };

    private final PhotoCategory[] photoCategories = {
            new PhotoCategory(
                    new String[]{"Nature", "Landscape", "Mountain", "Forest", "Lake", "Sunset", "Sunrise"},
                    new String[]{"nature", "landscape", "mountain", "forest", "lake", "sunset", "sunrise", "trees", "sky", "clouds", "peaceful", "serene"}
            ),
            new PhotoCategory(
                    new String[]{"Portrait", "People", "Woman", "Man", "Child", "Family", "Friends"},
                    new String[]{"portrait", "people", "woman", "man", "child", "family", "friends", "smile", "happy", "person", "face", "human"}
            ),
            new PhotoCategory(
                    new String[]{"Food", "Coffee", "Restaurant", "Cooking", "Meal", "Dessert", "Breakfast"},
                    new String[]{"food", "coffee", "restaurant", "cooking", "meal", "dessert", "breakfast", "delicious", "tasty", "cuisine", "dining"}
            ),
            new PhotoCategory(
                    new String[]{"Architecture", "Building", "City", "Street", "Urban", "Bridge", "Skyscraper"},
                    new String[]{"architecture", "building", "city", "street", "urban", "bridge", "skyscraper", "modern", "structure", "design"}
            ),
            new PhotoCategory(
                    new String[]{"Travel", "Beach", "Ocean", "Vacation", "Adventure", "Destination", "Tourism"},
                    new String[]{"travel", "beach", "ocean", "vacation", "adventure", "destination", "tourism", "explore", "journey", "wanderlust"}
            ),
            new PhotoCategory(
                    new String[]{"Art", "Creative", "Design", "Color", "Abstract", "Painting", "Photography"},
                    new String[]{"art", "creative", "design", "color", "abstract", "painting", "photography", "artistic", "visual", "inspiration"}
            ),
            new PhotoCategory(
                    new String[]{"Technology", "Computer", "Phone", "Digital", "Innovation", "Gadget", "Modern"},
                    new String[]{"technology", "computer", "phone", "digital", "innovation", "gadget", "modern", "tech", "electronic", "device"}
            ),
            new PhotoCategory(
                    new String[]{"Animals", "Dog", "Cat", "Wildlife", "Pet", "Bird", "Horse"},
                    new String[]{"animals", "dog", "cat", "wildlife", "pet", "bird", "horse", "cute", "adorable", "furry", "nature"}
            ),
            new PhotoCategory(
                    new String[]{"Fashion", "Style", "Clothing", "Model", "Outfit", "Trendy", "Accessories"},
                    new String[]{"fashion", "style", "clothing", "model", "outfit", "trendy", "accessories", "beauty", "elegant", "chic"}
            ),
            new PhotoCategory(
                    new String[]{"Sports", "Fitness", "Exercise", "Running", "Gym", "Healthy", "Active"},
                    new String[]{"sports", "fitness", "exercise", "running", "gym", "healthy", "active", "workout", "athletic", "strength"}
            )
    };

    private final String[] pexelsImageUrls = {
            "https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg",
            "https://images.pexels.com/photos/1040881/pexels-photo-1040881.jpeg",
            "https://images.pexels.com/photos/1226302/pexels-photo-1226302.jpeg",
            "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg",
            "https://images.pexels.com/photos/1456951/pexels-photo-1456951.jpeg",
            "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
            "https://images.pexels.com/photos/1714208/pexels-photo-1714208.jpeg",
            "https://images.pexels.com/photos/1816654/pexels-photo-1816654.jpeg",
            "https://images.pexels.com/photos/1933873/pexels-photo-1933873.jpeg",
            "https://images.pexels.com/photos/2065490/pexels-photo-2065490.jpeg",
            "https://images.pexels.com/photos/2253275/pexels-photo-2253275.jpeg",
            "https://images.pexels.com/photos/2387793/pexels-photo-2387793.jpeg",
            "https://images.pexels.com/photos/2448749/pexels-photo-2448749.jpeg",
            "https://images.pexels.com/photos/2662116/pexels-photo-2662116.jpeg",
            "https://images.pexels.com/photos/2792157/pexels-photo-2792157.jpeg",
            "https://images.pexels.com/photos/2850290/pexels-photo-2850290.jpeg",
            "https://images.pexels.com/photos/3165335/pexels-photo-3165335.jpeg",
            "https://images.pexels.com/photos/3228727/pexels-photo-3228727.jpeg",
            "https://images.pexels.com/photos/3408744/pexels-photo-3408744.jpeg",
            "https://images.pexels.com/photos/3532551/pexels-photo-3532551.jpeg",
            "https://images.pexels.com/photos/3686769/pexels-photo-3686769.jpeg",
            "https://images.pexels.com/photos/3747486/pexels-photo-3747486.jpeg",
            "https://images.pexels.com/photos/3844788/pexels-photo-3844788.jpeg",
            "https://images.pexels.com/photos/4145190/pexels-photo-4145190.jpeg",
            "https://images.pexels.com/photos/4348401/pexels-photo-4348401.jpeg",
            "https://images.pexels.com/photos/4425878/pexels-photo-4425878.jpeg",
            "https://images.pexels.com/photos/4577543/pexels-photo-4577543.jpeg",
            "https://images.pexels.com/photos/4672555/pexels-photo-4672555.jpeg",
            "https://images.pexels.com/photos/4792729/pexels-photo-4792729.jpeg",
            "https://images.pexels.com/photos/4925916/pexels-photo-4925916.jpeg",
            "https://images.pexels.com/photos/5077043/pexels-photo-5077043.jpeg",
            "https://images.pexels.com/photos/5434555/pexels-photo-5434555.jpeg",
            "https://images.pexels.com/photos/5690317/pexels-photo-5690317.jpeg",
            "https://images.pexels.com/photos/5727953/pexels-photo-5727953.jpeg",
            "https://images.pexels.com/photos/5876695/pexels-photo-5876695.jpeg",
            "https://images.pexels.com/photos/6069057/pexels-photo-6069057.jpeg",
            "https://images.pexels.com/photos/6153354/pexels-photo-6153354.jpeg",
            "https://images.pexels.com/photos/6387842/pexels-photo-6387842.jpeg",
            "https://images.pexels.com/photos/6457515/pexels-photo-6457515.jpeg",
            "https://images.pexels.com/photos/6612564/pexels-photo-6612564.jpeg",
            "https://images.pexels.com/photos/6667985/pexels-photo-6667985.jpeg",
            "https://images.pexels.com/photos/6953876/pexels-photo-6953876.jpeg",
            "https://images.pexels.com/photos/7001718/pexels-photo-7001718.jpeg",
            "https://images.pexels.com/photos/7155688/pexels-photo-7155688.jpeg",
            "https://images.pexels.com/photos/7257489/pexels-photo-7257489.jpeg",
            "https://images.pexels.com/photos/7389047/pexels-photo-7389047.jpeg",
            "https://images.pexels.com/photos/7517391/pexels-photo-7517391.jpeg",
            "https://images.pexels.com/photos/7658356/pexels-photo-7658356.jpeg",
            "https://images.pexels.com/photos/7794075/pexels-photo-7794075.jpeg",
            "https://images.pexels.com/photos/7956388/pexels-photo-7956388.jpeg"
    };

    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (imageRepository.count() > 0) {
            System.out.println("Photos collection already contains data. Skipping seed generation.");
            return;
        }

        System.out.println("Generating seed data...");

        // Create users and profiles
        List<Profile> profiles = createUsersAndProfiles();

        System.out.println("Generating 300 photos seed data...");

        // Create profile map for photo generation
        Map<String, Profile> profileMap = profiles.stream()
                .collect(Collectors.toMap(Profile::get_id, profile -> profile));

        List<Image> photos = generatePhotosSeedData(profileMap);

        // Save all photos
        List<Image> savedImages = imageRepository.saveAll(photos);

            savedImages.forEach(img -> {
                try {
                    imageService.generateImageThumbnail(img.get_id());
                } catch (Exception e) {
                    System.out.println("Failed for generate thumbnail. Skipped.");
                    System.out.println(e);
                }
            });


        System.out.println("Successfully generated and saved " + photos.size() + " photos!");
        printDistributionStats(photos);

        // Generate collections based on similar tags
        System.out.println("\nGenerating collections...");
        List<Collection> collections = generateCollections(photos, profiles);
        collectionRepository.saveAll(collections);

        System.out.println("Successfully generated and saved " + collections.size() + " collections!");
        printCollectionStats(collections);
    }

    private List<Profile> createUsersAndProfiles() {
        List<Profile> profiles = new ArrayList<>();

        for (String[] userData : seedUserData) {
            String email = userData[0];
            String fullName = userData[1];
            String username = userData[2];
            String avatar = userData[3];

            // Create user
            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.USER)
                    .build();

            user = userRepository.save(user);
            System.out.println("Created user: " + email);

            // Create profile
            Profile profile = Profile.builder()
                    .username(username)
                    .fullName(fullName)
                    .avatar(avatar)
                    .followers(new ArrayList<>())
                    .user(user)
                    .build();

            profile = profileRepository.save(profile);
            profiles.add(profile);
            System.out.println("Created profile: " + username + " (ID: " + profile.get_id() + ")");
        }

        return profiles;
    }

    private List<Image> generatePhotosSeedData(Map<String, Profile> profileMap) {
        List<Image> photos = new ArrayList<>();
        List<String> profileIds = new ArrayList<>(profileMap.keySet());

        // Calculate random distribution across profiles
        double[] profileWeights = {random.nextDouble(), random.nextDouble(), random.nextDouble()};
        double totalWeight = Arrays.stream(profileWeights).sum();

        // Normalize weights
        for (int i = 0; i < profileWeights.length; i++) {
            profileWeights[i] = profileWeights[i] / totalWeight;
        }

        // Calculate counts for each profile
        int[] profileCounts = new int[profileIds.size()];
        int totalAssigned = 0;

        for (int i = 0; i < profileIds.size() - 1; i++) {
            profileCounts[i] = (int) Math.floor(profileWeights[i] * 300);
            totalAssigned += profileCounts[i];
        }

        // Assign remaining photos to last profile
        profileCounts[profileIds.size() - 1] = 300 - totalAssigned;

        System.out.println("Distribution across profiles:");
        for (int i = 0; i < profileIds.size(); i++) {
            System.out.println("Profile " + (i + 1) + " (" + profileIds.get(i) + "): " + profileCounts[i] + " photos");
        }

        // Generate photos for each profile
        for (int profileIndex = 0; profileIndex < profileIds.size(); profileIndex++) {
            String profileId = profileIds.get(profileIndex);
            Profile profile = profileMap.get(profileId);
            int count = profileCounts[profileIndex];

            for (int i = 0; i < count; i++) {
                Image photo = createRandomPhoto(profile);
                photos.add(photo);
            }
        }

        return photos;
    }

    private List<Collection> generateCollections(List<Image> photos, List<Profile> profiles) {
        List<Collection> collections = new ArrayList<>();

        // Group photos by common tags
        Map<String, List<Image>> tagGroups = new HashMap<>();

        // Define key tags that will form collections
        String[] keyTags = {"nature", "portrait", "food", "architecture", "travel",
                "art", "technology", "animals", "fashion", "sports"};

        // Group photos by key tags
        for (String tag : keyTags) {
            List<Image> taggedPhotos = photos.stream()
                    .filter(photo -> photo.getTags().stream()
                            .anyMatch(t -> t.equalsIgnoreCase(tag)))
                    .collect(Collectors.toList());

            if (!taggedPhotos.isEmpty()) {
                tagGroups.put(tag, taggedPhotos);
            }
        }

        // Create collections from tag groups
        for (Map.Entry<String, List<Image>> entry : tagGroups.entrySet()) {
            String tag = entry.getKey();
            List<Image> groupPhotos = entry.getValue();

            // Create 2-4 collections per tag category
            int collectionsPerTag = 2 + random.nextInt(3);

            for (int i = 0; i < collectionsPerTag && !groupPhotos.isEmpty(); i++) {
                Profile author = getRandomElement(profiles.toArray(new Profile[0]));

                // Select 5-15 random photos from this tag group
                int photoCount = Math.min(5 + random.nextInt(11), groupPhotos.size());
                Collections.shuffle(groupPhotos);
                List<Image> collectionImages = groupPhotos.subList(0, photoCount);

                // Create collection title
                String title = generateCollectionTitle(tag, i + 1);

                // Create collection with random date in 2023-2024
                Collection collection = Collection.builder()
                        .author(author.getUsername())
                        .title(title)
                        .dateCreated(Date.from(getRandomDate()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                        .images(new ArrayList<>(collectionImages))
                        .build();

                collections.add(collection);

                // Remove used photos to avoid too much overlap
                if (groupPhotos.size() > photoCount) {
                    groupPhotos = groupPhotos.subList(photoCount, groupPhotos.size());
                }
            }
        }

        // Create some mixed collections with multiple tags
        for (int i = 0; i < 5; i++) {
            Profile author = getRandomElement(profiles.toArray(new Profile[0]));

            // Pick 2-3 random tags
            List<String> selectedTags = getRandomElements(keyTags, 2 + random.nextInt(2));

            List<Image> mixedPhotos = photos.stream()
                    .filter(photo -> selectedTags.stream()
                            .anyMatch(tag -> photo.getTags().stream()
                                    .anyMatch(t -> t.equalsIgnoreCase(tag))))
                    .collect(Collectors.toList());

            if (!mixedPhotos.isEmpty()) {
                Collections.shuffle(mixedPhotos);
                int photoCount = Math.min(8 + random.nextInt(13), mixedPhotos.size());
                List<Image> collectionImages = mixedPhotos.subList(0, photoCount);

                String title = String.join(" & ", selectedTags.stream()
                        .map(tag -> tag.substring(0, 1).toUpperCase() + tag.substring(1))
                        .collect(Collectors.toList())) + " Collection";

                Collection collection = Collection.builder()
                        .author(author.getUsername())
                        .title(title)
                        .dateCreated(Date.from(getRandomDate()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                        .images(new ArrayList<>(collectionImages))
                        .build();

                collections.add(collection);
            }
        }

        return collections;
    }

    private String generateCollectionTitle(String tag, int index) {
        String[] titleTemplates = {
                "Best of %s",
                "Amazing %s",
                "%s Inspiration",
                "Beautiful %s",
                "%s Collection",
                "Stunning %s",
                "%s Gallery",
                "My %s Favorites",
                "%s Masterpieces",
                "Curated %s"
        };

        String template = getRandomElement(titleTemplates);
        String capitalizedTag = tag.substring(0, 1).toUpperCase() + tag.substring(1);
        return String.format(template, capitalizedTag);
    }

    private Image createRandomPhoto(Profile profile) {
        PhotoCategory category = getRandomElement(photoCategories);
        String label = getRandomElement(category.labels);
        List<String> tags = getRandomElements(category.tags, 3 + random.nextInt(5));
        String url = getRandomElement(pexelsImageUrls);
        LocalDateTime dateAdded = getRandomDate();
        int likes = getRandomLikes();

        return Image.builder()
                .label(label)
                .tags(tags)
                .url(url)
                .date_added(dateAdded)
                .likes(likes)
                .profile(profile.get_id())
                .build();
    }

    private <T> T getRandomElement(T[] array) {
        return array[random.nextInt(array.length)];
    }

    private List<String> getRandomElements(String[] array, int count) {
        List<String> list = new ArrayList<>(Arrays.asList(array));
        Collections.shuffle(list);
        return list.stream().limit(Math.min(count, list.size())).collect(Collectors.toList());
    }

    private LocalDateTime getRandomDate() {
        int year = 2023 + random.nextInt(2); // 2023 or 2024
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28); // Safe day range for all months
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    private int getRandomLikes() {
        double rand = random.nextDouble();

        if (rand < 0.4) {
            return random.nextInt(11); // 0-10 likes
        } else if (rand < 0.7) {
            return 11 + random.nextInt(40); // 11-50 likes
        } else if (rand < 0.85) {
            return 51 + random.nextInt(50); // 51-100 likes
        } else if (rand < 0.95) {
            return 101 + random.nextInt(400); // 101-500 likes
        } else {
            return 501 + random.nextInt(500); // 501-1000 likes
        }
    }

    private void printDistributionStats(List<Image> photos) {
        Map<String, Long> profileDistribution = photos.stream()
                .collect(Collectors.groupingBy(
                        photo -> photo.getProfile(),
                        Collectors.counting()
                ));

        System.out.println("\nFinal distribution:");
        profileDistribution.forEach((profileId, count) ->
                System.out.println("Profile " + profileId + ": " + count + " photos"));

        // Likes distribution
        Map<String, Long> likesDistribution = photos.stream()
                .collect(Collectors.groupingBy(
                        photo -> {
                            int likes = photo.getLikes();
                            if (likes <= 10) return "0-10";
                            else if (likes <= 50) return "11-50";
                            else if (likes <= 100) return "51-100";
                            else if (likes <= 500) return "101-500";
                            else return "501+";
                        },
                        Collectors.counting()
                ));

        System.out.println("\nLikes distribution:");
        likesDistribution.forEach((range, count) ->
                System.out.println(range + " likes: " + count + " photos"));
    }

    private void printCollectionStats(List<Collection> collections) {
        System.out.println("\nCollection statistics:");
        System.out.println("Total collections: " + collections.size());

        Map<String, Long> authorDistribution = collections.stream()
                .collect(Collectors.groupingBy(
                        Collection::getAuthor,
                        Collectors.counting()
                ));

        System.out.println("\nCollections per author:");
        authorDistribution.forEach((author, count) ->
                System.out.println(author + ": " + count + " collections"));

        int totalImages = collections.stream()
                .mapToInt(c -> c.getImages().size())
                .sum();
        double avgImagesPerCollection = (double) totalImages / collections.size();

        System.out.println("\nAverage images per collection: " +
                String.format("%.2f", avgImagesPerCollection));

        System.out.println("\nSample collections:");
        collections.stream()
                .limit(5)
                .forEach(c -> System.out.println("  - " + c.getTitle() +
                        " by " + c.getAuthor() +
                        " (" + c.getImages().size() + " images)"));
    }

    // Helper class for photo categories
    private static class PhotoCategory {
        private final String[] labels;
        private final String[] tags;

        public PhotoCategory(String[] labels, String[] tags) {
            this.labels = labels;
            this.tags = tags;
        }
    }
}