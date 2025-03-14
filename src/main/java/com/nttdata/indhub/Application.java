package com.nttdata.indhub;

import com.nttdata.indhub.model.github.CommentRequest;
import com.nttdata.indhub.model.github.Content;
import com.nttdata.indhub.model.github.IssueComment;
import com.nttdata.indhub.model.github.PullRequest;
import com.nttdata.indhub.service.CodeAnalyzer;
import com.nttdata.indhub.service.GitHubConnector;
import com.nttdata.indhub.util.SSLUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class Application {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);

    String owner = "ericfaya";
    String groqApiKey = args[0];
    String groqModel = args[1];
    String githubToken = args[2];
    //int pullRequestId = Integer.parseInt(args[3]);
    String repoId = args[3];
    int pullRequestId;

    if (args.length > 4) {
      pullRequestId = Integer.parseInt(args[4]);
    } else {
      String pullRequestIdEnv = System.getenv("PR_NUMBER");
      System.out.println("Valor de PR_NUMBER desde env: " + pullRequestIdEnv);
      if (pullRequestIdEnv == null || pullRequestIdEnv.isEmpty()) {
        throw new IllegalArgumentException("No se encontró la variable de entorno PR_NUMBER");
      }
      pullRequestId = Integer.parseInt(pullRequestIdEnv);
    }

    // Deshabilitar la verificación SSL si es necesario
    SSLUtil.disableSSLVerification();

    CodeAnalyzer analyzer = new CodeAnalyzer();
    GitHubConnector gitHubConnector = new GitHubConnector();

    List<com.nttdata.indhub.model.github.File> files = gitHubConnector.getPRFiles(owner, repoId, pullRequestId, githubToken);

    PullRequest prDetails = gitHubConnector.getPullRequestDetails(owner, repoId, githubToken, pullRequestId);
    String prSha = prDetails.getHead().getSha();
    String prShaBase = prDetails.getBase().getSha();
    StringBuffer oldClass = new StringBuffer();
    StringBuffer newClass = new StringBuffer();

    // Código viejo y nuevo para análisis
    for (com.nttdata.indhub.model.github.File file : files) {

      switch (file.getStatus().toLowerCase()) {
        case "added":
          System.out.println("Added");

          Content content = gitHubConnector.getFileContent(owner, repoId, file.getFilename(), prSha, githubToken);
          String downloadUrl = content.getDownloadUrl();

          if (downloadUrl == null || downloadUrl.isEmpty()) {
            System.out.println("Download URL is null or empty for file(added): " + file.getFilename());
          } else {
            downloadFromUrl(downloadUrl, "current_" + file.getFilename());
          }
          break;

        case "renamed":
          //Todo no analizar o si el nombre
          break;
        case "modified":
        case "changed":
          System.out.println("Changed");
          Content content2 = gitHubConnector.getFileContent(owner, repoId, file.getFilename(), prSha, githubToken);
          String downloadUrl2 = content2.getDownloadUrl();
          if (downloadUrl2 == null || downloadUrl2.isEmpty()) {
            System.out.println("Download URL is null or empty for file(changed): " + file.getFilename());
          } else {
            oldClass = downloadFromUrl(content2.getDownloadUrl(), "current_" + file.getFilename());
            newClass = downloadFromUrl(gitHubConnector.getFileContent(owner, repoId, file.getFilename(), prShaBase, githubToken).getDownloadUrl(), "previous_" + file.getFilename());
            System.out.println("Contenido antiguo: " + oldClass.length());
            System.out.println("Contenido nuevo: " + newClass.length());

          }

          break;
        case "removed":
          break;
        default:
      }

      System.out.println("Processing file: " + file.getFilename());

    }
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    //CodeAnalyzer analyzer = new CodeAnalyzer(groqApiKey, groqModel);
    String analysis2 = analyzer.analyzeCode(oldClass.toString(), newClass.toString());
    System.out.println("Análisis del código:");
    System.out.println("====================");

    CommentRequest newComment = new CommentRequest( analysis2 +
            "\n CodeGuardian análisis ejecutado en: " + timestamp + " modelo :"+groqModel
    );

    // Publicar el comentario
    IssueComment createdComment = gitHubConnector.createPRComment(
            owner,
            repoId,
            pullRequestId,
            githubToken,
            newComment
    );
    System.out.println("Comentario creado con ID: " + createdComment.getId());
/*
        // Obtener todos los comentarios existentes
        List<IssueComment> existingComments = gitHubConnector.getPRComments(
                owner,
                repoId,
                pullRequestId,
                githubToken
        );

        ///TODO quitamos el historial de comentarios
        System.out.println("Comentarios existentes en la PR:");
        for (IssueComment comment : existingComments) {
            System.out.println(" - [" + comment.getCreatedAt() + "] " +
                    comment.getUser().getLogin() + ": " +
                    comment.getBody());
        }

         */
  }

  public static StringBuffer downloadFromUrl(String downloadUrl, String outputFileName) throws Exception {
    if (downloadUrl == null || downloadUrl.isEmpty()) {
      throw new IllegalArgumentException("The download URL cannot be null or empty");
    }
    URL url = new URL(downloadUrl);
    StringBuffer content = new StringBuffer();

    File outputFile = new File(outputFileName);
    File parentDir = outputFile.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs();  // Crea los directorios necesarios
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
         FileOutputStream fos = new FileOutputStream(outputFile)) {

      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        content.append(inputLine).append("\n");
        fos.write((inputLine + "\n").getBytes());
      }
    }

    return content;
  }
}