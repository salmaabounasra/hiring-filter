import java.io.*;
import java.util.*;
//Criteria Selected for Hiring a Java Programmer:
//1. Major: Focus on CS, CE, CIS, IST for foundational programming skills.
//2. Java Proficiency: Essential for the role; ensures candidate's familiarity with the primary language.
//3. GPA: Indicator of academic performance; higher GPA suggests consistency and dedication.
//4. Other Languages: Proficiency in additional programming languages indicates versatility.

//Why These Criteria?
//- These are quantifiable and relevant for an entry-level Java role.
//- Helps objectively assess technical qualifications.

//Limitations of Automated Hiring:
//- Misses Soft Skills: Communication, teamwork, adaptability not assessed.
//- Overlooks Non-traditional Backgrounds: Self-taught or bootcamp graduates might be skipped.
//- Ignores Work Experience/Projects: Practical experience and personal projects are not considered.
//- Risks in Diversity/Inclusion: May favor certain universities/backgrounds, reducing diversity.
//- Neglects Adaptability/Learning Potential: Ability to learn and grow with the company is not measured.

//Summary:
//Automated systems are efficient for handling large volumes but should be supplemented with interviews and other assessments to ensure a holistic evaluation, considering soft skills, diversity, and potential for growth.


public class HiringFilter {

    static class Applicant {
        String firstName;
        String lastName;
        String major;
        String languages;
        double GPA;
        double score;

        public Applicant(String firstName, String lastName, String major, String languages, double GPA) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.major = major;
            this.languages = languages;
            this.GPA = GPA;
            this.score = calculateScore();
        }

        private double calculateScore() {
            double score = 0;

            // Major (40% of total score)
            if (Arrays.asList("CS", "CE", "CIS", "IST").contains(major)) {
                score += 40;
            }

            // Java Proficiency (30% of total score)
            if (languages.contains("Java")) {
                score += 30;
            }

            // GPA (20% of total score)
            score += (GPA / 4.0) * 20;

            // Other Programming Languages (10% of total score)
            String[] otherLanguages = languages.split(" ");
            int additionalLanguages = (languages.contains("Java")) ? otherLanguages.length - 1 : otherLanguages.length;
            score += Math.min(additionalLanguages, 10);

            return score;
        }

        @Override
        public String toString() {
            return "Applicant{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", major='" + major + '\'' +
                    ", languages='" + languages + '\'' +
                    ", GPA=" + GPA +
                    ", score=" + score +
                    '}';
        }
    }

    public static List<Applicant> readApplicantsFromFile(String filename) throws IOException {
        List<Applicant> applicants = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            // Ensure correct mapping of CSV fields
            String firstName = values[1].trim();
            String lastName = values[0].trim();
            String major = values[9].trim();
            String languages = values[7].trim();
            double GPA;

            try {
                GPA = Double.parseDouble(values[8].trim());
            } catch (NumberFormatException e) {
                // Skip applicant if GPA is invalid
                continue;
            }

            applicants.add(new Applicant(firstName, lastName, major, languages, GPA));
        }

        br.close();
        return applicants;
    }

    public static void main(String[] args) {
        try {
            List<Applicant> applicants = readApplicantsFromFile("applicant_data.csv"); // Replace with actual file path

            // Sort applicants based on score
            applicants.sort((a1, a2) -> Double.compare(a2.score, a1.score));

            // Print top 10 applicants
            for (int i = 0; i < Math.min(10, applicants.size()); i++) {
                System.out.println(applicants.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

