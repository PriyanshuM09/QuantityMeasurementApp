package com.app.repository;

import java.io.EOFException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.app.model.QuantityMeasurementEntity;



class AppendableObjectOutputStream extends ObjectOutputStream {
	public AppendableObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
		if (!file.exists() || file.length() == 0) {
			super.writeStreamHeader();
		} else {
			reset();
		}
	}
}

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
	public static final String FILE_NAME = "quantity_measurement_repo.ser";

	// Holds the cached QuantityMeasurementEntity objects in memory for quick access
	List<QuantityMeasurementEntity> quantityMeasurementEntityCache;

	// Singleton instance of the repository
	private static QuantityMeasurementCacheRepository instance;

	// Private constructor to prevent instantiation from outside the class
	public QuantityMeasurementCacheRepository() {
		// Initialize the in-memory cache
		quantityMeasurementEntityCache = new java.util.ArrayList<>();
		// Load any existing data from disk
		loadFromDisk();
	}
    // Get single instance of repository
    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }
	// Save entity in memory and disk
    @Override
    public void save(QuantityMeasurementEntity entity) {
        // Add entity to memory cache
        quantityMeasurementEntityCache.add(entity);
        // Save entity to disk
        saveToDisk(entity);
    }

    // Return all stored entities
    @Override
    public List<QuantityMeasurementEntity> getAllMeasurement() {

        // Return cached list
        return quantityMeasurementEntityCache;
    }

    // Save entity to file
    private void saveToDisk(QuantityMeasurementEntity entity) {

        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
            AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos);

            // Write object to file
            oos.writeObject(entity);

            oos.close();
            fos.close();

        } catch (IOException e) {
            System.err.println("Error saving entity: " + e.getMessage());
        }
    }

    // Load entities from disk when repository starts
    private void loadFromDisk() {

        File file = new File(FILE_NAME);

        // If file does not exist, nothing to load
        if (!file.exists()) {
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    QuantityMeasurementEntity entity =
                            (QuantityMeasurementEntity) ois.readObject();

                    // Add entity to cache
                    quantityMeasurementEntityCache.add(entity);

                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }

            ois.close();
            fis.close();

            System.out.println("Loaded " +
                    quantityMeasurementEntityCache.size() +
                    " quantity measurement entities from storage");

        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(
                    "Error loading quantity measurement entities: " +
                            ex.getMessage()
            );
        }
    }
	@Override
	public List<QuantityMeasurementEntity> getMeasurementByOperation(String Operation) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<QuantityMeasurementEntity> getMeasurementByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void deleteALl() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getPoolStatistic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void releaseResource() {
		// TODO Auto-generated method stub
		
	}
}