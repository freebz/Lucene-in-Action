# 예제 10.4  키노서치 기반으로 색인 생성

use KinoSearch::InvIndexer;
use KinoSearch::Analysis::PolyAnalyzer;
my $analyzer
  = KinoSearch::Analysis::PolyAnalyzer->new( language => 'en' );

my $invindexer = KinoSearch::InvIndexer->new(
  invindex => '/path/to/invindex',
  create   => 1,
  analyzer => $analyzer,
);

$invindexer->spec_field(
  name  => 'title',
  boost => 3,
);
$invindexer->spec_field( name => 'bodytext' );

while ( my ( $title, $bodytext ) = each %source_documents ) {
  my $doc = $invindexer->new_doc;

  $doc->set_value( title    => $title );
  $doc->set_value( bodytext => $bodytext );

  $invindexer->add_doc($doc);
}

$invindexer->finish;
