package src;

import comp1110.lib.Pair;
import static comp1110.lib.Functions.*;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.Scanner;

/**
 * COMP1110/1140/6710 Assignment 1
 * 
 * This program simulates space scavengers discovering and trading artifacts recovered from deep space.
 * It models different types of artifacts (star charts, energy crystals, and inert rocks) and two types
 * of scavenger behaviors (rational and risk-taking) when encountering new artifacts or trading with others.
 */
public class Artifacts {

    /**
     * Enumeration representing possible analysis results when a scavenger evaluates an artifact.
     * These results determine how a scavenger will interact with the artifact.
     */
    enum AnalysisResult {
        VALUABLE,    // Artifact is considered worth acquiring
        HAZARDOUS,   // Artifact is considered dangerous
        MUNDANE,     // Artifact is considered ordinary/unspecial
        INCOMPATIBLE, // Artifact is considered incompatible with current cargo
        UNKNOWN      // Artifact cannot be properly analyzed
    }
    
    /**
     * Interface representing all types of artifacts in the simulation.
     * All artifacts must implement this interface to provide type identification.
     */
    interface Artifact {
        /**
         * Returns the type of artifact (STAR_CHART, ENERGY_CRYSTAL, or INERT_ROCK)
         * @return The ArtifactType enum value for this artifact
         */
        ArtifactType getType();
    }
    
    /**
     * Enumeration defining the three types of artifacts in the simulation.
     */
    enum ArtifactType {
        STAR_CHART,    // A navigational chart with destination information
        ENERGY_CRYSTAL, // A power source with measurable energy levels
        INERT_ROCK     // A non-reactive rock with distinctive coloration
    }
    
    /**
     * Represents a star chart artifact - a complex navigation tool containing
     * information about space destinations, risk levels, and location coordinates.
     */
    static class StarChart implements Artifact {
        private final String destination;  // The celestial destination marked on the chart
        private final int riskFactor;      // Numerical risk assessment (higher = more dangerous)
        private final int sector;          // Galactic sector coordinate
        private final int system;          // Star system coordinate within the sector
        
        /**
         * Constructs a new StarChart artifact with specific navigation parameters
         * @param destination Name of the destination location
         * @param riskFactor Numerical risk assessment (typically 1-10)
         * @param sector Galactic sector coordinate
         * @param system Star system coordinate within the sector
         */
        public StarChart(String destination, int riskFactor, int sector, int system) {
            this.destination = destination;
            this.riskFactor = riskFactor;
            this.sector = sector;
            this.system = system;
        }
        
        /** @return The destination marked on this star chart */
        public String getDestination() {
            return destination;
        }
        
        /** @return The risk factor associated with this destination */
        public int getRiskFactor() {
            return riskFactor;
        }
        
        /** @return The galactic sector coordinate */
        public int getSector() {
            return sector;
        }
        
        /** @return The star system coordinate within the sector */
        public int getSystem() {
            return system;
        }
        
        /**
         * Returns the artifact type as STAR_CHART
         * @return ArtifactType.STAR_CHART
         */
        @Override
        public ArtifactType getType() {
            return ArtifactType.STAR_CHART;
        }
    }
    
    /**
     * Represents an energy crystal artifact - a concentrated energy source
     * with a measurable power level that determines its value and utility.
     */
    static class EnergyCrystal implements Artifact {
        private final int powerLevel;  // The energy output rating of the crystal
        
        /**
         * Constructs a new EnergyCrystal with a specific power level
         * @param powerLevel The energy output rating (typically 1-20)
         */
        public EnergyCrystal(int powerLevel) {
            this.powerLevel = powerLevel;
        }
        
        /** @return The power level of this energy crystal */
        public int getPowerLevel() {
            return powerLevel;
        }
        
        /**
         * Returns the artifact type as ENERGY_CRYSTAL
         * @return ArtifactType.ENERGY_CRYSTAL
         */
        @Override
        public ArtifactType getType() {
            return ArtifactType.ENERGY_CRYSTAL;
        }
    }
    
    /**
     * Represents an inert rock artifact - a non-reactive mineral formation
     * distinguished by its color, which may indicate hidden properties.
     */
    static class InertRock implements Artifact {
        private final String color;  // The dominant color of the rock
        
        /**
         * Constructs a new InertRock with a specific color
         * @param color The color description of the rock (e.g., "blue", "red")
         */
        public InertRock(String color) {
            this.color = color;
        }
        
        /** @return The color of this inert rock */
        public String getColor() {
            return color;
        }
        
        /**
         * Returns the artifact type as INERT_ROCK
         * @return ArtifactType.INERT_ROCK
         */
        @Override
        public ArtifactType getType() {
            return ArtifactType.INERT_ROCK;
        }
    }
    
    /**
     * Implements the rational scavenger's analysis protocol for evaluating new artifacts.
     * This method follows a systematic decision tree based on artifact types and their
     * specific attributes to determine if a new artifact is valuable, hazardous, etc.
     * 
     * @param ownedArtifact The artifact the scavenger currently possesses
     * @param newArtifact The newly discovered artifact being evaluated
     * @return AnalysisResult indicating the scavenger's assessment of the new artifact
     */
    static AnalysisResult rationalScavengerAnalysis(Artifact ownedArtifact, Artifact newArtifact) {
        System.out.println("\n----- Rational Scavenger Analysis Process -----");
        System.out.println("Analyzing currently held item: " + describeArtifact(ownedArtifact) + " and new item: " + describeArtifact(newArtifact));
        
        // Handle case where owned artifact is a StarChart
        if (ownedArtifact.getType() == ArtifactType.STAR_CHART) {
            StarChart ownedChart = (StarChart) ownedArtifact;
            System.out.println("Currently holding star chart - Destination: " + ownedChart.getDestination() + ", Risk: " + ownedChart.getRiskFactor() + ", Sector: " + ownedChart.getSector() + ", System: " + ownedChart.getSystem());
            
            // Evaluate new EnergyCrystal against owned StarChart
            if (newArtifact.getType() == ArtifactType.ENERGY_CRYSTAL) {
                EnergyCrystal crystal = (EnergyCrystal) newArtifact;
                System.out.println("Discovered energy crystal - Power: " + crystal.getPowerLevel());
                
                // Check if star chart has ideal risk/system configuration
                if ((ownedChart.getRiskFactor() > 7 && ownedChart.getSystem() > 10) || 
                    (ownedChart.getRiskFactor() <= 7 && ownedChart.getSystem() <= 10)) {
                    System.out.println("Star chart is particularly valuable - ideal risk and system configuration");
                    
                    // Evaluate crystal based on power level thresholds
                    if (crystal.getPowerLevel() < 5) {
                        System.out.println("Low power energy crystal (power < 5) considered hazardous");
                        return AnalysisResult.HAZARDOUS;
                    } else if (crystal.getPowerLevel() < 15) {
                        System.out.println("Medium power energy crystal (power 5-14) considered mundane");
                        return AnalysisResult.MUNDANE;
                    } else {
                        System.out.println("High power energy crystal (power >=15) considered incompatible");
                        return AnalysisResult.INCOMPATIBLE;
                    }
                } else {
                    System.out.println("Star chart has regular value");
                    
                    // Alternative evaluation criteria for regular value star charts
                    if (crystal.getPowerLevel() >= 10) {
                        System.out.println("High power energy crystal (power >=10) considered valuable");
                        return AnalysisResult.VALUABLE;
                    } else if (crystal.getPowerLevel() < 5) {
                        System.out.println("Low power energy crystal (power <5) considered hazardous");
                        return AnalysisResult.HAZARDOUS;
                    } else {
                        System.out.println("Medium power energy crystal (power 5-9) considered mundane");
                        return AnalysisResult.MUNDANE;
                    }
                }
            }
            // Evaluate new InertRock against owned StarChart
            else if (newArtifact.getType() == ArtifactType.INERT_ROCK) {
                InertRock rock = (InertRock) newArtifact;
                System.out.println("Discovered inert rock - Color: " + rock.getColor());
                
                // Evaluate based on rock color
                if (rock.getColor().equals("blue") || rock.getColor().equals("green") || rock.getColor().equals("purple")) {
                    System.out.println("Special color rock (blue/green/purple) considered valuable");
                    return AnalysisResult.VALUABLE;
                } else if (rock.getColor().equals("red")) {
                    System.out.println("Red rock considered hazardous");
                    return AnalysisResult.HAZARDOUS;
                } else {
                    System.out.println("Other color rock considered mundane");
                    return AnalysisResult.MUNDANE;
                }
            }
            // Evaluate new StarChart against owned StarChart
            else if (newArtifact.getType() == ArtifactType.STAR_CHART) {
                StarChart newChart = (StarChart) newArtifact;
                System.out.println("Discovered new star chart - Destination: " + newChart.getDestination() + ", Risk: " + newChart.getRiskFactor() + ", Sector: " + newChart.getSector() + ", System: " + newChart.getSystem());
                
                // Evaluate based on destination, risk factor, and coordinates
                if (newChart.getDestination().equals(ownedChart.getDestination())) {
                    System.out.println("Star charts with same destination considered incompatible");
                    return AnalysisResult.INCOMPATIBLE;
                } else if (newChart.getRiskFactor() >= ownedChart.getRiskFactor() + 2) {
                    System.out.println("High risk star chart (risk +2 or more) considered hazardous");
                    return AnalysisResult.HAZARDOUS;
                } else if (newChart.getSector() == ownedChart.getSector() && newChart.getSystem() != ownedChart.getSystem()) {
                    System.out.println("Star charts with same sector different system considered valuable");
                    return AnalysisResult.VALUABLE;
                } else {
                    System.out.println("Other star chart cases considered mundane");
                    return AnalysisResult.MUNDANE;
                }
            }
        }
        
        // Handle comparisons between StarChart and EnergyCrystal in either order
        if ((ownedArtifact.getType() == ArtifactType.STAR_CHART && newArtifact.getType() == ArtifactType.ENERGY_CRYSTAL) ||
            (ownedArtifact.getType() == ArtifactType.ENERGY_CRYSTAL && newArtifact.getType() == ArtifactType.STAR_CHART)) {
            // If new artifact is star chart, result is hazardous; otherwise mundane
            return (newArtifact.getType() == ArtifactType.STAR_CHART) ? 
                AnalysisResult.HAZARDOUS : AnalysisResult.MUNDANE;
        }
        
        // Default case for unhandled artifact combinations
        System.out.println("Cannot perform specific analysis, returning unknown result");
        return AnalysisResult.UNKNOWN;
    }
    
    /**
     * Implements the risk-taking scavenger's analysis protocol for evaluating new artifacts.
     * This method follows simpler, more risk-tolerant decision logic compared to the rational protocol.
     * 
     * @param ownedArtifact The artifact the scavenger currently possesses
     * @param newArtifact The newly discovered artifact being evaluated
     * @return AnalysisResult indicating the scavenger's assessment of the new artifact
     */
    static AnalysisResult riskTakerScavengerAnalysis(Artifact ownedArtifact, Artifact newArtifact) {
        // Get types of both artifacts for comparison
        ArtifactType ownedType = ownedArtifact.getType();
        ArtifactType newType = newArtifact.getType();
        
        // Risk-takers always value new star charts
        if (newType == ArtifactType.STAR_CHART) {
            return AnalysisResult.VALUABLE;
        }
        
        // If owning a star chart, other artifact types are considered mundane
        if (ownedType == ArtifactType.STAR_CHART) {
            return AnalysisResult.MUNDANE;
        }
        
        // Compare two energy crystals based on power levels
        if (ownedType == ArtifactType.ENERGY_CRYSTAL && newType == ArtifactType.ENERGY_CRYSTAL) {
            EnergyCrystal ownedCrystal = (EnergyCrystal) ownedArtifact;
            EnergyCrystal newCrystal = (EnergyCrystal) newArtifact;
            // More powerful crystals are valued
            return (newCrystal.getPowerLevel() > ownedCrystal.getPowerLevel()) ? 
                AnalysisResult.VALUABLE : AnalysisResult.MUNDANE;
        }
        
        // Compare two inert rocks based on color
        if (ownedType == ArtifactType.INERT_ROCK && newType == ArtifactType.INERT_ROCK) {
            InertRock ownedRock = (InertRock) ownedArtifact;
            InertRock newRock = (InertRock) newArtifact;
            
            // Same color is mundane
            if (ownedRock.getColor().equals(newRock.getColor())) {
                return AnalysisResult.MUNDANE;
            } else {
                // Different colors decided by random chance
                return (new Random().nextBoolean()) ? 
                    AnalysisResult.VALUABLE : AnalysisResult.INCOMPATIBLE;
            }
        }
        
        // Unhandled combinations result in unknown
        return AnalysisResult.UNKNOWN;
    }
    
    // Helper methods for evaluating analysis results
    /** @return true if the result is VALUABLE */
    static boolean isValuable(AnalysisResult result) {
        return result == AnalysisResult.VALUABLE;
    }
    
    /** @return true if the result is HAZARDOUS */
    static boolean isHazardous(AnalysisResult result) {
        return result == AnalysisResult.HAZARDOUS;
    }
    
    /** @return true if the result is MUNDANE */
    static boolean isMundane(AnalysisResult result) {
        return result == AnalysisResult.MUNDANE;
    }
    
    /** @return true if the result is INCOMPATIBLE */
    static boolean isIncompatible(AnalysisResult result) {
        return result == AnalysisResult.INCOMPATIBLE;
    }
    
    /** @return true if the result is UNKNOWN */
    static boolean isUnknown(AnalysisResult result) {
        return result == AnalysisResult.UNKNOWN;
    }
    
    // Factory methods for creating artifacts
    /**
     * Creates a new StarChart artifact
     * @param dest Destination name
     * @param risk Risk factor value
     * @param sector Sector coordinate
     * @param system System coordinate
     * @return New StarChart instance
     */
    static Artifact makeStarChart(String dest, int risk, int sector, int system) {
        return new StarChart(dest, risk, sector, system);
    }
    
    /**
     * Creates a new EnergyCrystal artifact
     * @param power Power level value
     * @return New EnergyCrystal instance
     */
    static Artifact makeEnergyCrystal(int power) {
        return new EnergyCrystal(power);
    }
    
    /**
     * Creates a new InertRock artifact
     * @param color Color description
     * @return New InertRock instance
     */
    static Artifact makeInertRock(String color) {
        return new InertRock(color);
    }
    
    /**
     * Represents a space scavenger with a name, current cargo, and personal protocol
     * for analyzing artifacts. Scavengers can discover new artifacts and trade with others.
     */
    static class Scavenger {
        private final String name;                        // Scavenger's identifying name
        private Artifact cargo;                           // Currently held artifact
        private final BiFunction<Artifact, Artifact, AnalysisResult> personalProtocol; // Method for analyzing artifacts
        
        /**
         * Constructs a new Scavenger with specified attributes
         * @param name Scavenger's name
         * @param personalProtocol Analysis method to use for evaluating artifacts
         * @param initialCargo Starting artifact
         */
        public Scavenger(String name, BiFunction<Artifact, Artifact, AnalysisResult> personalProtocol, Artifact initialCargo) {
            this.name = name;
            this.personalProtocol = personalProtocol;
            this.cargo = initialCargo;
        }
        
        /** @return The scavenger's name */
        public String getName() {
            return name;
        }
        
        /** @return The artifact currently being carried */
        public Artifact getCargo() {
            return cargo;
        }
        
        /**
         * Updates the scavenger's current cargo
         * @param artifact The new artifact to carry
         */
        public void setCargo(Artifact artifact) {
            this.cargo = artifact;
        }
        
        /** @return The scavenger's personal analysis protocol */
        public BiFunction<Artifact, Artifact, AnalysisResult> getPersonalProtocol() {
            return personalProtocol;
        }
    }
    
    /**
     * Simulates a scavenger exploring an asteroid and discovering a new artifact.
     * The scavenger uses their personal analysis protocol to evaluate the new artifact
     * and decides whether to keep it, discard their current cargo, or avoid it entirely.
     * 
     * @param scavenger The scavenger exploring the asteroid
     * @param foundArtifact The newly discovered artifact
     * @return A Pair containing the updated scavenger and the artifact left behind
     */
    static Pair<Scavenger, Artifact> exploreAsteroid(Scavenger scavenger, Artifact foundArtifact) {
        System.out.println("\n----- Exploring Asteroid -----");
        System.out.println("Scavenger: " + scavenger.getName());
        System.out.println("Currently holding: " + describeArtifact(scavenger.getCargo()));
        System.out.println("Held item details: " + getDetailedArtifactInfo(scavenger.getCargo()));
        System.out.println("Discovered item: " + describeArtifact(foundArtifact));
        System.out.println("Discovered item details: " + getDetailedArtifactInfo(foundArtifact));
        
        // Analyze the new artifact using the scavenger's protocol
        AnalysisResult result = scavenger.getPersonalProtocol().apply(scavenger.getCargo(), foundArtifact);
        System.out.println("Analysis result: " + translateAnalysisResult(result) + " (" + result + ")");
        
        // Determine action based on analysis result
        System.out.println("Executing decision based on analysis result:");
        switch (result) {
            case VALUABLE:
                // Replace current cargo with the valuable new artifact
                System.out.println("Discovered item is more valuable, deciding to replace current cargo");
                System.out.println("Discarding: " + describeArtifact(scavenger.getCargo()));
                System.out.println("Acquiring: " + describeArtifact(foundArtifact));
                Artifact oldCargo = scavenger.getCargo();
                scavenger.setCargo(foundArtifact);
                return new Pair<>(scavenger, oldCargo);
                
            case HAZARDOUS:
                // 30% chance to take the hazardous item, 70% chance of cargo destruction
                Random random = new Random();
                double chance = random.nextDouble();
                System.out.println("Item determined to be hazardous, making risk decision (random value: " + String.format("%.2f", chance) + ")");
                
                if (chance < 0.3) { 
                    System.out.println("Item is hazardous, but scavenger decides to take the risk (30% success probability)");
                    Artifact oldCargoHazard = scavenger.getCargo();
                    scavenger.setCargo(foundArtifact);
                    return new Pair<>(scavenger, oldCargoHazard);
                } else {
                    System.out.println("Item is hazardous, scavenger decides not to take risk (70% failure probability)");
                    System.out.println("Current cargo destroyed by hazardous radiation");
                    System.out.println("Original item: " + describeArtifact(scavenger.getCargo()) + " replaced with dull grey inert rock");
                    scavenger.setCargo(makeInertRock("dull grey"));
                    return new Pair<>(scavenger, foundArtifact);
                }
                
            case MUNDANE:
                // Ignore the mundane item
                System.out.println("Discovered item is mundane, deciding to ignore and keep current cargo");
                System.out.println("Keeping: " + describeArtifact(scavenger.getCargo()));
                break;
                
            case INCOMPATIBLE:
                // Ignore incompatible item
                System.out.println("Discovered item is incompatible with current cargo, deciding to ignore");
                System.out.println("Keeping: " + describeArtifact(scavenger.getCargo()));
                break;
                
            case UNKNOWN:
                // Ignore unknown item for safety
                System.out.println("Cannot analyze discovered item, deciding to ignore for safety");
                System.out.println("Keeping: " + describeArtifact(scavenger.getCargo()));
                break;
                
            default:
                // Default to ignoring the item
                System.out.println("Other case, scavenger decides to ignore discovered item");
                System.out.println("Keeping: " + describeArtifact(scavenger.getCargo()));
        }
        return new Pair<>(scavenger, foundArtifact);
    }
    
    /**
     * Simulates a trading interaction between two scavengers at a starport.
     * A trade occurs only if both scavengers independently determine that
     * the other's artifact is valuable according to their personal protocols.
     * 
     * @param scavengerA First scavenger participating in the trade
     * @param scavengerB Second scavenger participating in the trade
     * @return A Pair containing the updated scavengers in their original order
     */
    static Pair<Scavenger, Scavenger> tradeAtStarport(Scavenger scavengerA, Scavenger scavengerB) {
        System.out.println("\n----- Trading at Starport -----");
        System.out.println("Starport lights flicker as two scavengers stand in negotiation area preparing to trade");
        System.out.println("Scavenger A (" + scavengerA.getName() + "):");
        System.out.println("  Holding: " + describeArtifact(scavengerA.getCargo()));
        System.out.println("  Details: " + getDetailedArtifactInfo(scavengerA.getCargo()));
        
        System.out.println("Scavenger B (" + scavengerB.getName() + "):");
        System.out.println("  Holding: " + describeArtifact(scavengerB.getCargo()));
        System.out.println("  Details: " + getDetailedArtifactInfo(scavengerB.getCargo()));
        
        // Get current cargo for both scavengers
        Artifact cargoA = scavengerA.getCargo();
        Artifact cargoB = scavengerB.getCargo();
        
        // Both analyze each other's cargo
        System.out.println("\nBoth parties begin analyzing each other's items...");
        AnalysisResult aAnalysisOfB = scavengerA.getPersonalProtocol().apply(cargoA, cargoB);
        AnalysisResult bAnalysisOfA = scavengerB.getPersonalProtocol().apply(cargoB, cargoA);
        
        System.out.println("Scavenger A's analysis of B's cargo: " + translateAnalysisResult(aAnalysisOfB) + " (" + aAnalysisOfB + ")");
        System.out.println("Scavenger B's analysis of A's cargo: " + translateAnalysisResult(bAnalysisOfA) + " (" + bAnalysisOfA + ")");
        
        // Determine if trade occurs (both must find each other's cargo valuable)
        System.out.println("\nEntering trade decision phase:");
        if (isValuable(aAnalysisOfB) && isValuable(bAnalysisOfA)) {
            System.out.println("Both parties consider each other's cargo valuable, reaching trade agreement");
            System.out.println("Scavenger A will receive: " + describeArtifact(cargoB));
            System.out.println("Scavenger B will receive: " + describeArtifact(cargoA));
            scavengerA.setCargo(cargoB);
            scavengerB.setCargo(cargoA);
        } else {
            System.out.println("Trade failed, at least one party does not consider the other's cargo valuable");
            if (!isValuable(aAnalysisOfB)) {
                System.out.println("Scavenger A does not consider B's cargo valuable");
            }
            if (!isValuable(bAnalysisOfA)) {
                System.out.println("Scavenger B does not consider A's cargo valuable");
            }
            System.out.println("Both parties keep their original cargo");
        }
        
        // Display post-trade status
        System.out.println("\nPost-trade status:");
        System.out.println("Scavenger A (" + scavengerA.getName() + "):");
        System.out.println("  Holding: " + describeArtifact(scavengerA.getCargo()));
        System.out.println("  Details: " + getDetailedArtifactInfo(scavengerA.getCargo()));
        
        System.out.println("Scavenger B (" + scavengerB.getName() + "):");
        System.out.println("  Holding: " + describeArtifact(scavengerB.getCargo()));
        System.out.println("  Details: " + getDetailedArtifactInfo(scavengerB.getCargo()));
        
        return new Pair<>(scavengerA, scavengerB);
    }
    
    // Helper methods for Scavenger class testing
    /**
     * Creates a new Scavenger with specified attributes
     * @param name Scavenger's name
     * @param personalProtocol Analysis protocol to use
     * @param initialCargo Starting artifact
     * @return New Scavenger instance
     */
    static Scavenger makeScavenger(String name, BiFunction<Artifact, Artifact, AnalysisResult> personalProtocol, Artifact initialCargo) {
        return new Scavenger(name, personalProtocol, initialCargo);
    }
    
    /**
     * Gets a scavenger's name
     * @param scavenger The scavenger
     * @return The scavenger's name
     */
    static String getName(Scavenger scavenger) {
        return scavenger.getName();
    }
    
    /**
     * Gets a scavenger's current cargo
     * @param scavenger The scavenger
     * @return The currently held artifact
     */
    static Artifact getCargo(Scavenger scavenger) {
        return scavenger.getCargo();
    }
    
    /**
     * Generates a standardized string description of an artifact containing
     * all relevant attributes in a machine-readable format.
     * 
     * @param artifact The artifact to describe
     * @return Formatted string description of the artifact
     */
    static String describeArtifact(Artifact artifact) {
        if (artifact instanceof StarChart) {
            StarChart chart = (StarChart) artifact;
            return "StarChart:" + chart.getDestination() + "; RISK=" + chart.getRiskFactor() 
                + "; SEC=" + chart.getSector() + "; SYS=" + chart.getSystem();
        } else if (artifact instanceof EnergyCrystal) {
            EnergyCrystal crystal = (EnergyCrystal) artifact;
            return "EnergyCrystal:POWER=" + crystal.getPowerLevel();
        } else if (artifact instanceof InertRock) {
            InertRock rock = (InertRock) artifact;
            return "InertRock:COLOR=" + rock.getColor();
        }
        return "Unknown Artifact";
    }
    
    /**
     * Parses a log entry describing a rational scavenger's encounter and simulates
     * the interaction to determine the scavenger's final cargo.
     * 
     * @param s Log entry string in specified format
     * @return The artifact the rational scavenger possesses after the encounter
     * @throws IllegalArgumentException if log format is invalid
     */
    static Artifact parseRationalScavengerLog(String s) {
        // Split log into components using " | " as delimiter
        String[] parts = s.split(" \\| ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid log format");
        }
        
        String encounterType = parts[0];
        String ownedArtifactStr = parts[1];
        String otherArtifactStr = parts[2];
        
        System.out.println("\n----- Parsing Log -----");
        System.out.println("Encounter type: " + translateEncounterType(encounterType));
        System.out.println("Owned item: " + ownedArtifactStr);
        System.out.println("Encountered item: " + otherArtifactStr);
        
        // Parse both artifacts from their string descriptions
        Artifact ownedArtifact = parseArtifactDescription(ownedArtifactStr);
        Artifact otherArtifact = parseArtifactDescription(otherArtifactStr);
        
        // Simulate appropriate encounter based on type
        if (encounterType.equals("ASTEROID")) {
            Scavenger rationalScavenger = makeScavenger("Rational Scavenger", Artifacts::rationalScavengerAnalysis, ownedArtifact);
            Pair<Scavenger, Artifact> result = exploreAsteroid(rationalScavenger, otherArtifact);
            return result.first().getCargo();
        } else if (encounterType.equals("TRADING_POST")) {
            Scavenger rationalScavenger = makeScavenger("Rational Scavenger", Artifacts::rationalScavengerAnalysis, ownedArtifact);
            Scavenger riskTaker = makeScavenger("Risk-taking Scavenger", Artifacts::riskTakerScavengerAnalysis, otherArtifact);
            Pair<Scavenger, Scavenger> result = tradeAtStarport(rationalScavenger, riskTaker);
            return result.first().getCargo();
        } else {
            throw new IllegalArgumentException("Unknown encounter type: " + encounterType);
        }
    }
    
    /**
     * Parses a string description of an artifact into the corresponding
     * Artifact object instance.
     * 
     * @param description Formatted string describing an artifact
     * @return Artifact instance matching the description
     * @throws IllegalArgumentException if description format is invalid
     */
    private static Artifact parseArtifactDescription(String description) {
        if (description.startsWith("StarChart:")) {
            // Parse StarChart format: StarChart:destination; RISK=risk; SEC=sector; SYS=system
            int destEnd = description.indexOf(";");
            String destination = description.substring(10, destEnd);
            
            int riskStart = description.indexOf("RISK=") + 5;
            int riskEnd = description.indexOf(";", riskStart);
            int risk = Integer.parseInt(description.substring(riskStart, riskEnd));
            
            int secStart = description.indexOf("SEC=") + 4;
            int secEnd = description.indexOf(";", secStart);
            int sector = Integer.parseInt(description.substring(secStart, secEnd));
            
            int sysStart = description.indexOf("SYS=") + 4;
            int system = Integer.parseInt(description.substring(sysStart));
            
            return makeStarChart(destination, risk, sector, system);
        } else if (description.startsWith("EnergyCrystal:")) {
            // Parse EnergyCrystal format: EnergyCrystal:POWER=power
            int powerStart = description.indexOf("POWER=") + 6;
            int power = Integer.parseInt(description.substring(powerStart));
            return makeEnergyCrystal(power);
        } else if (description.startsWith("InertRock:")) {
            // Parse InertRock format: InertRock:COLOR=color
            int colorStart = description.indexOf("COLOR=") + 6;
            String color = description.substring(colorStart);
            return makeInertRock(color);
        } else {
            throw new IllegalArgumentException("Unknown artifact type: " + description);
        }
    }
    
    /**
     * Main method that processes user input as a log entry, simulates the
     * described encounter, and displays the final result.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Print program information and instructions
        System.out.println("*************************************************************");
        System.out.println("*             Space Scavenger Simulation Program             *");
        System.out.println("*************************************************************");
        System.out.println("This program simulates scenarios where space scavengers encounter and trade different items in space.");
        System.out.println("Two encounter types can be simulated:");
        System.out.println("1. ASTEROID - Discovering items on an asteroid");
        System.out.println("2. TRADING_POST - Trading with another scavenger at a starport\n");
        System.out.println("Item types include:");
        System.out.println("- StarChart: Starmap containing destination, risk factor, sector and system information");
        System.out.println("- EnergyCrystal: Energy crystal containing power level");
        System.out.println("- InertRock: Inert rock containing color information\n");
        
        // Read user input log entry
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a log entry in the following format:");
        System.out.println("ASTEROID | [Owned item description] | [Discovered item description]");
        System.out.println("or");
        System.out.println("TRADING_POST | [Owned item description] | [Trading partner's item description]");
        System.out.println("\nItem description formats:");
        System.out.println("StarChart:destination; RISK=riskValue; SEC=sectorValue; SYS=systemValue");
        System.out.println("EnergyCrystal:POWER=powerValue");
        System.out.println("InertRock:COLOR=color");
        System.out.println("\nExample input:");
        System.out.println("ASTEROID | StarChart:Alpha Centauri; RISK=5; SEC=3; SYS=7 | EnergyCrystal:POWER=10");
        System.out.print("\nPlease enter: ");
        String logEntry = scanner.nextLine();
        System.out.println("\n===== Starting Log Entry Processing =====");
        System.out.println("Received log: " + logEntry);
        
        try {
            // Process log entry and display result
            Artifact finalArtifact = parseRationalScavengerLog(logEntry);
            
            System.out.println("\n===== Final Result =====");
            System.out.println("Final cargo: " + describeArtifact(finalArtifact));
            System.out.println("Detailed information: " + getDetailedArtifactInfo(finalArtifact));
            System.out.println("\nSimulation complete!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts an AnalysisResult enum to its display string
     * @param result The analysis result to translate
     * @return String representation of the analysis result
     */
    static String translateAnalysisResult(AnalysisResult result) {
        switch (result) {
            case VALUABLE:
                return "Valuable";
            case HAZARDOUS:
                return "Hazardous";
            case MUNDANE:
                return "Mundane";
            case INCOMPATIBLE:
                return "Incompatible";
            case UNKNOWN:
                return "Unknown";
            default:
                return "Undefined";
        }
    }
    
    /**
     * Converts an encounter type string to its display description
     * @param type The encounter type to translate
     * @return String description of the encounter type
     */
    static String translateEncounterType(String type) {
        if (type.equals("ASTEROID")) {
            return "Asteroid Exploration";
        } else if (type.equals("TRADING_POST")) {
            return "Starport Trading";
        } else {
            return "Unknown Type";
        }
    }

    /**
     * Generates a human-readable detailed description of an artifact
     * @param artifact The artifact to describe
     * @return Detailed string description of the artifact
     */
    static String getDetailedArtifactInfo(Artifact artifact) {
        if (artifact == null) {
            return "No item";
        }
        
        switch (artifact.getType()) {
            case STAR_CHART:
                StarChart chart = (StarChart) artifact;
                return String.format("Star Chart [Destination: %s, Risk Factor: %d, Sector: %d, System: %d]", 
                                    chart.getDestination(), chart.getRiskFactor(), 
                                    chart.getSector(), chart.getSystem());
            
            case ENERGY_CRYSTAL:
                EnergyCrystal crystal = (EnergyCrystal) artifact;
                return String.format("Energy Crystal [Power: %d]", crystal.getPowerLevel());
            
            case INERT_ROCK:
                InertRock rock = (InertRock) artifact;
                return String.format("Inert Rock [Color: %s]", rock.getColor());
            
            default:
                return "Unknown item";
        }
    }
}
